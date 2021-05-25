package com.wushi.lade.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wushi.lade.LadeTestsApplication;
import com.wushi.lade.tests.entity.dm.Address;
import com.wushi.lade.tests.service.dm.DmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 达梦单元测试
 *
 * @author wushi
 * @date 2020/2/3 15:51
 */
@SpringBootTest(classes = LadeTestsApplication.class)
public class DmTest {

    @Autowired
    private DmService dmService;

    /**
     * 查询全部
     *
     * @return void
     * @author wushi
     * @date 2020/2/3 11:23
     */
    @Test
    public void selectAll() {
        List<Map<String, Object>> list = dmService.listMaps();
        System.out.println(list.toString());
    }

    /**
     * 按名称查询
     *
     * @return void
     * @author wushi
     * @date 2020/2/3 11:23
     */
    @Test
    public void selectByName() {
        System.out.println("=======【开始】select方法【按条件查询】测试=======");
        // 按条件查询【Mybatis-Plus封装方法】
        List<Address> addressList = dmService.list(new QueryWrapper<Address>().lambda().eq(Address::getName, "汉字"));
        System.out.println("按条件查询：" + addressList);
        System.out.println("=======【结束】select方法【按条件查询】测试=======");
    }

    /**
     * Like条件查询
     *
     * @return void
     * @author wushi
     * @date 2020/2/3 11:36
     */
    @Test
    public void selectByLike() {
        System.out.println("=======【开始】select方法【按条件查询】测试=======");
        // 按条件查询【Mybatis-Plus封装方法】
        List<Address> addressList = dmService.list(new QueryWrapper<Address>().lambda().like(Address::getName, "汉"));
        System.out.println("按条件查询：" + addressList);
        System.out.println("=======【结束】select方法【按条件查询】测试=======");
    }

    /**
     * 分页查询【无条件】
     *
     * @return void
     * @author wushi
     * @date 2020/1/8 11:14
     */
    @Test
    public void selectByPage() {
        System.out.println("=======【开始】select方法【分页查询】测试=======");
        // 1代表当前页，2代表每页显示条数
        Page<Address> pageConfig = new Page<>(1, 2);
        Page<Address> addressPage = dmService.page(pageConfig);
        List<Address> addressPageRecords = addressPage.getRecords();
        System.out.println("分页查询结果：" + addressPageRecords);
        System.out.println("=======【开始】select方法【分页查询】测试=======");
    }

    /**
     * 通用插入
     *
     * @return com.wushi.lade.tests.entity.address
     * @author wushi
     * @date 2020/1/8 10:41
     */
    private Address insertAddress() {
        Address address = new Address();
        address.setName("lhx");
        boolean save = dmService.save(address);
        if (save) {
            System.out.println("插入成功：" + address);
        } else {
            System.out.println("插入失败");
        }
        return address;
    }

    /**
     * 插入
     *
     * @return void
     * @author wushi
     * @date 2020/2/5 8:49
     */
    @Test
    public void insert() {
        insertAddress();
    }

    /**
     * 批量插入【暂不可用】
     *
     * @return void
     * @author wushi
     * @date 2020/2/5 8:49
     */
    @Test
    public void insertBatch() {
        System.out.println("=======【开始】Insert方法【批量插入】测试=======");
        List<Address> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Address address = new Address();
            address.setName("name");
            list.add(address);
        }
        // 批量插入测试
        Address address = new Address();
        address.setName("aaa");

        Address address1 = new Address();
        address1.setName("aaa");

        dmService.saveBatch(Arrays.asList(address, address1));
        System.out.println("=======【结束】Insert方法【批量插入】测试=======");
    }

    /**
     * 更新记录【按条件】
     *
     * @return void
     * @author wushi
     * @date 2020/1/8 16:06
     */
    @Test
    public void update() {
        System.out.println("=======【开始】Update方法【按条件】测试=======");
        // 创建Update条件
        UpdateWrapper<Address> updateWrapper = new UpdateWrapper<>();
        // 创建Set条件，Where id=? 条件
        updateWrapper.lambda().set(Address::getName, "汉字1")
                .eq(Address::getName, "汉字");
        boolean bol = dmService.update(updateWrapper);
        if (bol) System.out.println("更新成功！");
        System.out.println("=======【结束】Update方法【按条件】测试=======");
    }

    /**
     * 按ID删除
     *
     * @return void
     * @author wushi
     * @date 2020/2/5 8:49
     */
    @Test
    public void deleteById() {
        System.out.println("=======【开始】Delete方法【按ID】测试=======");
        // 新增一条自增记录
        Address address = insertAddress();
        int id = address.getId();
        // 删除这条记录
        boolean b = dmService.removeById(id);
        if (b) {
            System.out.println("成功删除记录，id:" + id);
        }
        System.out.println("=======【开始】Delete方法【按ID】测试=======");
    }
}
