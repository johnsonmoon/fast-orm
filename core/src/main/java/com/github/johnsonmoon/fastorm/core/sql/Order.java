package com.github.johnsonmoon.fastorm.core.sql;

import com.github.johnsonmoon.fastorm.core.util.StringUtils;

/**
 * 排序
 * <p>
 * Created by xuyh at 2017/9/22 15:11.
 */
public class Order {
    private final String DIRECTION_ASC = "ASC";
    private final String DIRECTION_DESC = "DESC";

    private String orderBy;

    private Order() {
        this.orderBy = "ORDER BY ";
    }

    /**
     * 获取排序条件
     *
     * @return string instance
     */
    public String getOrder() {
        return orderBy.trim();
    }

    /**
     * 创建排序条件
     *
     * @param fields order fields
     * @return {@link Order}
     */
    public static Order orderBy(String... fields) {
        if (fields == null || fields.length == 0)
            return new Order();
        Order order = new Order();
        String fieldsStr = "";
        for (String field : fields) {
            fieldsStr += (StringUtils.getSureName(field) + ", ");
        }
        fieldsStr = fieldsStr.substring(0, fieldsStr.length() - 2);
        order.orderBy += (fieldsStr + " ");
        return order;
    }

    /**
     * 升序排序
     *
     * @return {@link Order}
     */
    public Order direction_ASC() {
        this.orderBy += (DIRECTION_ASC + " ");
        return this;
    }

    /**
     * 降序排序
     *
     * @return {@link Order}
     */
    public Order direction_DESC() {
        this.orderBy += (DIRECTION_DESC + " ");
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "DIRECTION_ASC='" + DIRECTION_ASC + '\'' +
                ", DIRECTION_DESC='" + DIRECTION_DESC + '\'' +
                ", orderBy='" + orderBy + '\'' +
                '}';
    }
}
