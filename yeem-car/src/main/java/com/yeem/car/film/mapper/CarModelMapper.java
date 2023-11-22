package com.yeem.car.film.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.car.film.entity.BaseCarModel;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
@DS("car-film-saas")
public interface CarModelMapper extends BaseMapper<BaseCarModel> {
    /**
     * id查询所有
     *
     * @return
     */
    List<BaseCarModel> list();

    /**
     * Id查询所有不被软删除的数据
     *
     * @return
     */
    List<BaseCarModel> listByBrandId(Long id);

    /**
     * 分页查询
     *
     * @param map
     * @return
     */
    List<BaseCarModel> pages(Map<String,Object> map);


    /**
     * id单独查询
     *
     * @param id
     * @return
     */
    BaseCarModel getById(Long id);

    /**
     * 删除
     *
     * @param id
     */
    void removeByBrandId(Long id);

    void remove(Long id);

    /**
     * 增加
     *
     * @param baseCarModelList
     */
    boolean save(List<BaseCarModel> baseCarModelList, Long brandId);

    int insert(BaseCarModel baseCarModel);

    /**
     * 修改
     *
     * @param baseCarModelList
     */
    void update(List<BaseCarModel> baseCarModelList);

}
