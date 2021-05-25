package com.wushi.lade.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wushi.lade.LadeTestsApplication;
import com.wushi.lade.tests.entity.gauss.TestByGauss;
import com.wushi.lade.tests.service.gauss.GaussService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Gauss单元测试
 *
 * @author wushi
 * @date 2020/1/7 15:51
 */
@SpringBootTest(classes = LadeTestsApplication.class)
public class GaussTest {

    @Autowired
    private GaussService gaussService;

    /**
     * 查询全部
     *
     * @return void
     * @author wushi
     * @date 2020/2/3 11:23
     */
    @Test
    public void selectAll() {
        List<TestByGauss> list = gaussService.list();
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
        List<TestByGauss> testByGaussList = gaussService.list(new QueryWrapper<TestByGauss>().lambda().eq(TestByGauss::getName, "汉字"));
        System.out.println("按条件查询：" + testByGaussList);
        System.out.println("=======【结束】select方法【按条件查询】测试=======");
    }

    /**
     * 分页查询【按条件查询】
     * 含：order by用法
     *
     * @return void
     * @author wushi
     * @date 2020/1/8 11:14
     */
    @Test
    public void selectByPageWithCondition() {
        System.out.println("=======【开始】select方法【按条件分页查询】测试=======");
        // 构造分页条件：1代表当前页，2代表每页显示条数
        Page<TestByGauss> pageConfig = new Page<>(1, 2);
        // 构造查询条件
        QueryWrapper<TestByGauss> queryWrapper = new QueryWrapper<>();
        // ORDER BY create_time DESC
        queryWrapper.lambda().orderByDesc(TestByGauss::getId);
        // Mybatis-Plus封装的Page对象
        Page<TestByGauss> testByGaussPage = gaussService.page(pageConfig, queryWrapper);
        List<TestByGauss> testByGaussList = testByGaussPage.getRecords();
        System.out.println("分页查询结果：" + testByGaussList);
        System.out.println("=======【结束】select方法【按条件分页查询】测试=======");

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
        List<TestByGauss> testByGaussList = gaussService.list(new QueryWrapper<TestByGauss>().lambda().like(TestByGauss::getName, "汉字"));
        System.out.println("按条件查询：" + testByGaussList);
        System.out.println("=======【结束】select方法【按条件查询】测试=======");
    }

    /**
     * 通用插入
     *
     * @return com.wushi.lade.tests.entity.testByGauss
     * @author wushi
     * @date 2020/1/8 10:41
     */
    private TestByGauss insertTestByGauss() {
        TestByGauss testByGauss = new TestByGauss();
        testByGauss.setName("lhx");
        testByGauss.setSex("1");
        testByGauss.setTelephone("110");
        boolean save = gaussService.save(testByGauss);
        if (save) {
            System.out.println("TestByGauss插入成功：" + testByGauss);
        } else {
            System.out.println("TestByGauss插入失败");
        }
        return testByGauss;
    }

    /**
     * 插入
     *
     * @return void
     * @author wushi
     * @date 2020/2/5 8:48
     */
    @Test
    public void insert() {
        insertTestByGauss();
    }

    /**
     * 批量插入
     *
     * @return void
     * @author wushi
     * @date 2020/2/5 8:48
     */
    @Test
    public void insertBatch() {
        System.out.println("=======【开始】Insert方法【批量插入】测试=======");
        List<TestByGauss> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TestByGauss testByGauss = new TestByGauss();
            testByGauss.setName("name_" + i);
            testByGauss.setSex("1");
            testByGauss.setTelephone("110");
            list.add(testByGauss);
        }
        // 批量插入测试
        gaussService.saveBatch(list);
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
        UpdateWrapper<TestByGauss> updateWrapper = new UpdateWrapper<>();
        // 创建Set条件，Where id=? 条件
        updateWrapper.lambda().set(TestByGauss::getName, "汉字1")
                .eq(TestByGauss::getName, "汉字");
        boolean bol = gaussService.update(updateWrapper);
        if (bol) System.out.println("更新成功！");
        System.out.println("=======【结束】Update方法【按条件】测试=======");
    }

    /**
     * 按ID删除
     *
     * @return void
     * @author wushi
     * @date 2020/2/5 8:48
     */
    @Test
    public void deleteById() {
        System.out.println("=======【开始】Delete方法【按ID】测试=======");
        // 新增一条自增记录
        TestByGauss testByGauss = insertTestByGauss();
        String id = testByGauss.getId();
        // 删除这条记录
        boolean b = gaussService.removeById(id);
        if (b) {
            System.out.println("成功删除记录，id:" + id);
        }
        System.out.println("=======【开始】Delete方法【按ID】测试=======");
    }
}
