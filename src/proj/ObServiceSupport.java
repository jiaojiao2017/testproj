package proj;

import java.io.Serializable;
import java.util.List;

/**  
 * @project  ob-util
 * @description Ob组扩展ServiceSupport 增,删,修model
 * @author xiuhong.wang
 * @create time 2011-10-19 下午07:46:53
 * @modify time 2011-10-19 下午07:46:53
 * @modify comment 
 * @version  
*/

public interface ObServiceSupport<T, PK extends Serializable> {

    /**
     * 
    * @Title: saveModel
    * @Description: 保存Model
    * @param model
    * @return    T
    * @throws
     */
    @SuppressWarnings("unchecked")
    public abstract T saveModel(final T model);

    /**
     * 
    * @Title: saveModelInIsolatedTx
    * @Description: save model 独立事务
    * @param model
    * @return    T
    * @throws
     */
    @SuppressWarnings("unchecked")
    public abstract T saveModelInIsolatedTx(final T model);

    /**
     * 
    * @Title: mergeModel
    * @Description: merge model
    * @param model
    * @return    T
    * @throws
     */
    public abstract T mergeModel(final T model);

    /**
     * 
    * @Title: mergeModelInIsolatedTx
    * @Description: merge model 独立事务
    * @param model
    * @return    T
    * @throws
     */
    public abstract T mergeModelInIsolatedTx(final T model);

    /**
     * 
    * @Title: deleteModel
    * @Description: 删除Model
    * @param model    void
    * @throws
     */
    @SuppressWarnings("unchecked")
    public abstract void deleteModel(final T model);

    /**
     * 
    * @Title: deleteModelIsolatedTx
    * @Description: 删除Model 独立事务
    * @param model    void
    * @throws
     */
    @SuppressWarnings("unchecked")
    public abstract void deleteModelIsolatedTx(final T model);

    /**
     * 
    * @Title: deleteModelByPk
    * @Description: 根据主键删除Model
    * @param id    void
    * @throws
     */
    public abstract void deleteModelByPk(final Object id);

    /**
     * 
    * @Title: deleteModelByPk
    * @Description: 根据主键删除Model 独立事务
    * @param id    void
    * @throws
     */
    public abstract void deleteModelByPkIsolatedTx(final Object id);

    @SuppressWarnings("unchecked")
    public abstract T findModelById(final Object id);

    @SuppressWarnings("rawtypes")
    public abstract List loadAllModels();


}