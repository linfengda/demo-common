package com.lfd.soa.common.orm;

import com.lfd.soa.common.exception.BusinessException;
import com.lfd.soa.common.orm.entity.AttributeValue;
import com.lfd.soa.common.orm.entity.ConditionParam;
import com.lfd.soa.common.orm.entity.SetValue;
import com.lfd.soa.common.orm.sql.builder.PreStatementSql;
import com.lfd.soa.common.orm.sql.builder.PreStatementSqlBuilder;
import com.lfd.soa.common.orm.sql.handler.PreStatementSqlHandler;
import com.lfd.soa.common.orm.utils.ClassUtil;
import com.lfd.soa.common.orm.utils.ResultSetUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

/**
 * 描述: 基础ORM服务
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public class OrmTemplate {
    private static DataSource dataSource;

    public static void save(Object po) throws Exception {
        PreStatementSqlHandler statement = null;
        try {
            //batch save
            if (po instanceof List) {
                throw new BusinessException("请使用batchSave方法保存list对象！");
            }
            String idName = ClassUtil.getIdName(po.getClass());
            AttributeValue idValue = ClassUtil.getValueByProperty(idName, po);
            PreStatementSql preSql;
            if (idValue.getValue() == null) {
                //simple save
                preSql = PreStatementSqlBuilder.INSTANCE.buildInsertSql(po);
            } else {
                //simple update
                preSql = PreStatementSqlBuilder.INSTANCE.buildUpdateSql(idValue, po);
            }
            statement = new PreStatementSqlHandler(preSql, dataSource);
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public static void batchSave(List<Object> poList) throws Exception {
        if (poList == null || poList.size() == 0) {
            return;
        }
        PreStatementSqlHandler statement = null;
        try {
            PreStatementSql preSql = PreStatementSqlBuilder.INSTANCE.buildBatchInsertSql(poList);
            statement = new PreStatementSqlHandler(preSql, dataSource);
            statement.executeBatchSave();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public static void update(Class<?> clazz, SetValue setValue, ConditionParam conditionParam) throws Exception {
        PreStatementSqlHandler statement = null;
        try {
            PreStatementSql preSql = PreStatementSqlBuilder.INSTANCE.buildUpdateSql(setValue, conditionParam, clazz);
            statement = new PreStatementSqlHandler(preSql, dataSource);
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public static void updateByPrimaryKey(Class<?> clazz, SetValue setValue, Integer id) throws Exception {
        String idName = ClassUtil.getIdName(clazz);
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add(idName, id);
        update(clazz, setValue, conditionParam);
    }

    public static <T> List<T> query(ConditionParam param, Class<T> clazz) throws Exception {
        PreStatementSqlHandler statement = null;
        try {
            PreStatementSql preSql = PreStatementSqlBuilder.INSTANCE.getQuerySqlByConditionParam(param, clazz, false);
            statement = new PreStatementSqlHandler(preSql, dataSource);
            ResultSet result = statement.executeQuery();
            List<T> recorders = ResultSetUtil.convertToListObject(clazz, result);

            return recorders;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public static <T> T get(ConditionParam param, Class<T> clazz) throws Exception {
        param.setPageNo(1);
        param.setPageSize(1);
        PreStatementSqlHandler statement = null;
        try {
            PreStatementSql preSql = PreStatementSqlBuilder.INSTANCE.getQuerySqlByConditionParam(param, clazz, true);
            statement = new PreStatementSqlHandler(preSql, dataSource);
            ResultSet result = statement.executeQuery();
            return ResultSetUtil.convertToObject(clazz, result);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public static <T> T getByPrimaryKey(Integer id, Class<T> clazz) throws Exception {
        PreStatementSqlHandler statement = null;
        try {
            PreStatementSql preSql = PreStatementSqlBuilder.INSTANCE.buildQuerySql(id, clazz);
            statement = new PreStatementSqlHandler(preSql, dataSource);
            ResultSet result = statement.executeQuery();
            return ResultSetUtil.convertToObject(clazz, result);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public static long countByParam(ConditionParam param, Class<?> clazz) throws Exception {
        PreStatementSqlHandler statement = null;
        try {
            PreStatementSql preSql = PreStatementSqlBuilder.INSTANCE.buildCountSql(param, clazz);
            statement = new PreStatementSqlHandler(preSql, dataSource);
            ResultSet result = statement.executeQuery();
            return ResultSetUtil.getLong("c", result);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public static void setDataSource(DataSource dataSource) {
        OrmTemplate.dataSource = dataSource;
    }
}
