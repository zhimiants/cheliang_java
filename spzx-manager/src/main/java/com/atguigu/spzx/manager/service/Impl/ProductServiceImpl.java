package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.manager.mapper.ProductDetailsMapper;
import com.atguigu.spzx.manager.mapper.ProductMapper;
import com.atguigu.spzx.manager.mapper.ProductSkuMapper;
import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductSkuMapper productSkuMapper;

    @Autowired
    ProductDetailsMapper productDetailsMapper;

    /**
     * 商品管理分页查询
     * @param pageNum
     * @param pageSize
     * @param productDto
     * @return
     */
    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize, ProductDto productDto) {
        PageHelper.startPage(pageNum,pageSize);
        List<Product> list = productMapper.selectList(productDto);

        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Product product) {
        //特殊: 需要添加3张表格的数据
        product.setStatus(0);//上下架状态
        product.setAuditStatus(0);//审核状态
        //增加商品基本信息数据 spu
        productMapper.add(product);

        //遍历增加商品sku数据
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (int i = 0; i < productSkuList.size(); i++) {
            //遍历
            ProductSku productSku = productSkuList.get(i);
            //设置SkuCode
            productSku.setSkuCode(product.getId() + "_" + i);
            //设置SkuName
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            //设置productId
            productSku.setProductId(product.getId());
            productSku.setSaleNum(0); //库存
            productSku.setStatus(0); //上下架状态
            productSkuMapper.add(productSku);

        }
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.add(productDetails);


    }
    //根据id获取商品信息  也是从3个表获取  为什么?因为要回显啊
    @Override
    public Product getById(Long id) {
        //从product  要封装3个表的信息
        // spu基本信息表
        Product product = productMapper.getById(id);

        //sku表
        List<ProductSku> productSkuList = productSkuMapper.getByProductId(id);
        product.setProductSkuList(productSkuList);


        ProductDetails productDetails = productDetailsMapper.getByProductId(id);
        //spu只要他的详情图
        String imageUrls = productDetails.getImageUrls();
        product.setDetailsImageUrls(imageUrls);
        return product;
    }

    @Override
    public void update(Product product) {
        //修改也是 关联3个表
        //spu
        productMapper.updateById(product);

        //spu
        List<ProductSku> productSkuList = product.getProductSkuList();
        productSkuList.forEach(productSku -> {
            productSkuMapper.updateById(productSku);
        });

        //detail
        //从product获取详情图
        String detailsImageUrls = product.getDetailsImageUrls();

        //ProductDetails productDetails = new ProductDetails();
        ProductDetails productDetails = productDetailsMapper.findByProductId(product.getId());
        //覆盖修改
        productDetails.setImageUrls(detailsImageUrls);
        productDetailsMapper.updateById(productDetails);




    }
    //删除
    @Override
    public void deleteById(Long id) {
        productMapper.deleteById(id);                   // 根据id删除商品基本数据
        productSkuMapper.deleteByProductId(id);         // 根据商品id删除商品的sku数据
        productDetailsMapper.deleteByProductId(id);     // 根据商品的id删除商品的详情数据
    }

    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {

        Product product = new Product();
        product.setId(id);
        if(auditStatus == 1){
            product.setAuditStatus(1);
            product.setAuditMessage("审核通过");
        }else{
            product.setAuditStatus(-1);
            product.setAuditMessage("审核未通过");
        }
        //没有参数就不改数据  这里只有id 和 审核状态和消息
        productMapper.updateById(product);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        if(status == 1) {
            product.setStatus(1);
        } else {
            product.setStatus(-1);
        }
        productMapper.updateById(product);
    }
}
