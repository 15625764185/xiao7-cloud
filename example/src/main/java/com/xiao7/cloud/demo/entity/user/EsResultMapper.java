package com.xiao7.cloud.demo.entity.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：xiao7
 * @date ：Created in 2020/6/7 22:42
 * @description：
 */
@Component
public class EsResultMapper implements SearchResultMapper {

    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
        // 记录总条数
        long totalHits = response.getHits().getTotalHits();
        // 记录列表(泛型) - 构建Aggregate使用
        List<T> list = new ArrayList<>();
        // 获取搜索结果(真正的的记录)
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            if (hits.getHits().length <= 0) {
                return null;
            }
            // 将原本的JSON对象转换成Map对象
            Map<String, Object> map = hit.getSourceAsMap();
            // 获取高亮的字段Map
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            for (Map.Entry<String, HighlightField> highlightField : highlightFields.entrySet()) {
                // 获取高亮的Key
                String key = highlightField.getKey();
                String[] keys = key.split(".");

                // 获取高亮的Value
                HighlightField value = highlightField.getValue();
                // 实际fragments[0]就是高亮的结果，无需遍历拼接
                Text[] fragments = value.getFragments();
                StringBuilder sb = new StringBuilder();
                for (Text text : fragments) {
                    sb.append(text);
                }
                // 因为高亮的字段必然存在于Map中，就是key值
                // 可能有一种情况，就是高亮的字段是嵌套Map，也就是说在Map里面还有Map的这种情况，这里没有考虑
                map.put("content", sb.toString().replaceAll("\n|\t", ""));
            }
            // 把Map转换成对象
            map.put("attachment", "");
            T item = JSON.parseObject(JSONObject.toJSONString(map), aClass);
            list.add(item);
        }
        // 返回的是带分页的结果
        return new AggregatedPageImpl<>(list, pageable, totalHits);
    }

    @Override
    public <T> T mapSearchHit(SearchHit searchHit, Class<T> type) {
        Map<String, Object> map = searchHit.getSourceAsMap();
        return JSON.parseObject(JSONObject.toJSONString(map), type);
    }

}
