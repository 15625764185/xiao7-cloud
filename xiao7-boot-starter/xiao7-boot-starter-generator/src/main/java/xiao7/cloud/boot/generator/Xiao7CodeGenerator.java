package xiao7.cloud.boot.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.xiao7.cloud.boot.base.utils.Func;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * 代码生成配置类
 *
 * @author xiao7
 */
@Data
public class Xiao7CodeGenerator {

  /** 代码模块名称 */
  private String codeName;

  /** 代码生成的包名 */
  private String packageName = "";

  /** 代码后端生成的地址 */
  private String packageDir;

  /** 需要去掉的表前缀 */
  private String[] tablePrefix;

  /** 需要生成的表名(两者只能取其一) */
  private String[] includeTables;

  /** 需要排除的表名(两者只能取其一) */
  private String[] excludeTables = {};

  /** 是否包含基础业务字段 */
  private Boolean hasSuperEntity = true;

  /** 是否包含包装器 */
  private Boolean hasWrapper;

  /** baseModel */
  private String superEntityClass = "com.xiao7.cloud.boot.mybatis.base.BaseModel";

  /** 基础业务字段 */
  private String[] superEntityColumns = {"id", "update_time", "create_time"};

  /** 数据库驱动名 */
  private String driverName;

  /** 数据库链接地址 */
  private String url;

  /** 数据库用户名 */
  private String username;

  /** 数据库密码 */
  private String password;

  /** 是否开启二级缓存 */
  private boolean enableCache = false;

  /** 业务模块名称 */
  private String moduleName = "";

  public void run() {
    // 全局配置
    Properties props = getProperties();
    AutoGenerator mpg = new AutoGenerator();
    GlobalConfig gc = new GlobalConfig();
    String author = props.getProperty("author");
    gc.setOutputDir(packageDir + "/src/main/java");
    gc.setAuthor(author);
    gc.setFileOverride(true);
    gc.setOpen(false);
    gc.setActiveRecord(false);
    gc.setEnableCache(isEnableCache());
    gc.setBaseResultMap(true);
    gc.setBaseColumnList(true);
    gc.setMapperName("%sMapper");
    gc.setXmlName("%sMapper");
    gc.setServiceName("I%sRepository");
    gc.setServiceImplName("%sRepositoryImpl");
    gc.setControllerName("%sController");
    mpg.setGlobalConfig(gc);

    // 数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    String driverName =
        Func.toStr(this.driverName, props.getProperty("spring.datasource.driver-class-name"));
    dsc.setDbType(DbType.MYSQL);
    dsc.setTypeConvert(new MySqlTypeConvert());
    dsc.setDriverName(driverName);
    dsc.setUrl(Func.toStr(this.url, props.getProperty("spring.datasource.url")));
    dsc.setUsername(Func.toStr(this.username, props.getProperty("spring.datasource.username")));
    dsc.setPassword(Func.toStr(this.password, props.getProperty("spring.datasource.password")));
    mpg.setDataSource(dsc);

    // 策略配置
    StrategyConfig strategy = new StrategyConfig();
    // strategy.setCapitalMode(true);// 全局大写命名
    // strategy.setDbColumnUnderline(true);//全局下划线命名
    strategy.setNaming(NamingStrategy.underline_to_camel);
    strategy.setColumnNaming(NamingStrategy.underline_to_camel);
    strategy.setTablePrefix(tablePrefix);
    if (includeTables.length > 0) {
      strategy.setInclude(includeTables);
    }
    if (excludeTables.length > 0) {
      strategy.setExclude(excludeTables);
    }
    if (hasSuperEntity) {
      strategy.setSuperEntityClass(superEntityClass);
      strategy.setSuperEntityColumns(superEntityColumns);
    }
    strategy.setEntityBuilderModel(false);
    strategy.setRestControllerStyle(true);
    strategy.setEntityTableFieldAnnotationEnable(true);
    strategy.setEntityLombokModel(true);
    strategy.setControllerMappingHyphenStyle(true);
    mpg.setStrategy(strategy);

    // 包配置
    PackageConfig pc = new PackageConfig();
    // 控制台扫描
    pc.setModuleName(null);
    // pc.setController("controller." + moduleName);
    pc.setMapper("mapper." + moduleName);
    pc.setXml("mapper." + moduleName);
    pc.setEntity("entity." + moduleName);
    pc.setService("service." + moduleName);
    pc.setServiceImpl("service." + moduleName + ".impl");
    pc.setParent(packageName);
    // 基础设施层
    mpg.setPackageInfo(pc);
    mpg.execute();
  }

  /**
   * 获取配置文件
   *
   * @return 配置Props
   */
  private Properties getProperties() {
    // 读取配置文件
    Resource resource = new ClassPathResource("/templates/code.properties");
    Properties props = new Properties();
    try {
      props = PropertiesLoaderUtils.loadProperties(resource);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return props;
  }
}
