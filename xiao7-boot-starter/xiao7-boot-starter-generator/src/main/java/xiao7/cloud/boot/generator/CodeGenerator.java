package xiao7.cloud.boot.generator;

/**
 * 代码生成器
 *
 * @author lcb
 */
public class CodeGenerator {

  /** 代码生成的模块名 */
  public static String CODE_NAME = "角色管理";

  /** 代码生成的包名 */
  public static String PACKAGE_NAME = "com.xiao7.cloud.demo";

  /** 需要去掉的表前缀 */
  public static String[] TABLE_PREFIX;

  /** 需要生成的表名(两者只能取其一) */
  public static String[] INCLUDE_TABLES = {"user"};

  /** 需要排除的表名(两者只能取其一) */
  public static String[] EXCLUDE_TABLES = {};

  /** 是否包含基础业务字段 */
  public static Boolean HAS_SUPER_ENTITY = Boolean.TRUE;

  /** 基础业务字段 */
  public static String[] SUPER_ENTITY_COLUMNS = {
    "id", "update_time", "create_time", "version", "is_delete"
  };

  public static String PACKAGE_DIR = "G:\\IntelliJ IDEA 2020.1\\workspace\\xiao7-cloud\\example";

  /** 业务模块名称 */
  public static String MODULE_NAME = "user";

  /** RUN THIS */
  public static void run() {
    Xiao7CodeGenerator generator = new Xiao7CodeGenerator();
    generator.setCodeName(CODE_NAME);
    generator.setPackageName(PACKAGE_NAME);
    generator.setTablePrefix(TABLE_PREFIX);
    generator.setPackageDir(PACKAGE_DIR);
    generator.setIncludeTables(INCLUDE_TABLES);
    generator.setExcludeTables(EXCLUDE_TABLES);
    generator.setHasSuperEntity(HAS_SUPER_ENTITY);
    generator.setSuperEntityColumns(SUPER_ENTITY_COLUMNS);
    generator.setModuleName(MODULE_NAME);
    generator.run();
  }

  public static void main(String[] args) {
    CodeGenerator.run();
  }
}
