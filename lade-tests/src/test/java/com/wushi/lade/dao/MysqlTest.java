package com.wushi.lade.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wushi.lade.LadeTestsApplication;
import com.wushi.lade.tests.entity.SysUser;
import com.wushi.lade.tests.entity.SysUserAccount;
import com.wushi.lade.tests.entity.mysql.TestAutoIncrementByMysql;
import com.wushi.lade.tests.entity.mysql.TestBatchInsertByMysql;
import com.wushi.lade.tests.service.ISysUserAccountService;
import com.wushi.lade.tests.service.ISysUserService;
import com.wushi.lade.tests.service.mysql.ITestAutoIncrementByMysqlService;
import com.wushi.lade.tests.service.mysql.ITestBatchInsertByMysqlService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Mysql单元测试
 *
 * @author wushi
 * @date 2020/1/7 15:51
 */
@SpringBootTest(classes = LadeTestsApplication.class)
public class MysqlTest {

    /**
     * 指定实现类为Mysql
     */
    @Resource(name = "sysUserByMysqlServiceImpl")
    private ISysUserService sysUserService;

    @Resource(name = "sysUserAccountByMysqlServiceImpl")
    private ISysUserAccountService sysUserAccountService;

    @Resource(name = "testAutoIncrementByMysqlServiceImpl")
    private ITestAutoIncrementByMysqlService testAutoIncrementService;

    @Resource(name = "testBatchInsertByMysqlServiceImpl")
    private ITestBatchInsertByMysqlService testBatchInsertService;

    /**
     * 查询全部
     *
     * @return void
     * @author wushi
     * @date 2020/1/8 10:24
     */
    @Test
    public void selectAll() {
        System.out.println("=======【开始】select方法【查询全部】测试=======");
        // Mysql查询全部
        List<SysUser> sysUserListAll = sysUserService.list();
        System.out.println("SysUser全部查询：" + sysUserListAll);
        System.out.println("=======【结束】select方法【查询全部】测试=======");
    }

    /**
     * 按条件查询
     *
     * @return void
     * @author wushi
     * @date 2020/1/8 10:25
     */
    @Test
    public void selectById() {
        System.out.println("=======【开始】select方法【按条件查询】测试=======");
        SysUser sysUser = insertSysUser();
        // Mysql按条件查询【Mybatis-Plus封装方法】
        List<SysUser> sysUserList = sysUserService.list(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserId, sysUser.getUserId()));
        System.out.println("SysUser按条件查询：" + sysUserList);
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
        Page<SysUser> pageConfig = new Page<>(1, 2);
        Page<SysUser> sysUserPage = sysUserService.page(pageConfig);
        List<SysUser> sysUserPageRecords = sysUserPage.getRecords();
        System.out.println("分页查询结果：" + sysUserPageRecords);
        System.out.println("=======【开始】select方法【分页查询】测试=======");
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
        Page<SysUser> pageConfig = new Page<>(1, 2);
        // 构造查询条件
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        // ORDER BY create_time DESC
        queryWrapper.lambda().orderByDesc(SysUser::getCreateTime);
        // Mybatis-Plus封装的Page对象
        Page<SysUser> sysUserPage = sysUserService.page(pageConfig, queryWrapper);
        List<SysUser> sysUserPageRecords = sysUserPage.getRecords();
        System.out.println("分页查询结果：" + sysUserPageRecords);
        System.out.println("=======【结束】select方法【按条件分页查询】测试=======");

    }

    /**
     * 查询总记录数
     *
     * @return void
     * @author wushi
     * @date 2020/1/8 11:57
     */
    @Test
    public void selectCount() {
        System.out.println("=======【开始】select方法【查询总记录数】测试=======");
        int count = sysUserService.count();
        System.out.println("SysUser总记录数：" + count);
        System.out.println("=======【结束】select方法【查询总记录数】测试=======");
    }

    /**
     * 查询总记录数【按条件】
     * 含：like表达式
     *
     * @return void
     * @author wushi
     * @date 2020/1/8 11:57
     */
    @Test
    public void selectCountByCondition() {
        System.out.println("=======【开始】select方法【按条件查询总记录数】测试=======");
        // 先插入数据，保证条件查询有值
        String realName = insertSysUser().getRealName();
        // 构造查询条件
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        // like表达式使用
        queryWrapper.lambda().like(SysUser::getRealName, realName);
        int count = sysUserService.count();
        System.out.println("SysUser中含有“" + realName + "”的总记录数：" + count);
        System.out.println("=======【结束】select方法【按条件查询总记录数】测试=======");
    }

    /**
     * SysUser通用插入
     *
     * @return SysUser
     * @author wushi
     * @date 2020/1/8 10:41
     */
    private SysUser insertSysUser() {
        // 构造SysUser入参
        SysUser sysUser = new SysUser();
        sysUser.setRealName("管理员");
        sysUser.setTelephone("110");
        sysUser.setIdCard("123456");
        sysUser.setSex("1");
        sysUser.setEmail("123456@qq.com");
        sysUser.setAddress("中国");
        sysUser.setRemark("备注");
        sysUser.setSortId(1);
        sysUser.setPinYin("GLY");
        sysUser.setHeadPortrail("");
        sysUser.setIsHidePhone(0);
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setUpdateTime(LocalDateTime.now());
        boolean save = sysUserService.save(sysUser);
        if (save) {
            System.out.println("SysUser插入成功：" + sysUser);
        } else {
            System.out.println("SysUser插入失败");
        }
        return sysUser;
    }

    /**
     * Insert单条记录，返回主键UUID
     *
     * @return void
     * @author wushi
     * @date 2020/1/8 14:23
     */
    @Test
    public void insertReturnUUID() {
        System.out.println("=======【开始】Insert方法【UUID】测试=======");
        // 构造SysUser入参
        SysUser sysUser = new SysUser();
        // String uuid = UUID.randomUUID().toString();
        // System.out.println("手动生成UUID：" + uuid);
        // 手动生成的UUID可以覆盖框架自动生成的UUID
        // sysUser.setUserId(uuid);
        sysUser.setRealName("管理员");
        sysUser.setTelephone("110");
        sysUser.setIdCard("123456");
        sysUser.setSex("1");
        sysUser.setEmail("123456@qq.com");
        sysUser.setAddress("中国");
        sysUser.setRemark("备注");
        sysUser.setSortId(1);
        sysUser.setPinYin("GLY");
        sysUser.setHeadPortrail("");
        sysUser.setIsHidePhone(0);
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setUpdateTime(LocalDateTime.now());
        // 此方法是Mybatis-Plus封装的
        /*
        boolean bol = sysUserService.save(sysUser);
        if (bol) {
            System.out.println("SysUser插入成功！");
        } else {
            System.out.println("SysUser插入失败！");
        }
        */
        // insert返回影响行数
        int affectRows = sysUserService.insertReturnAffectRow(sysUser);
        System.out.println("SysUser-insert影响行数：" + affectRows);
        // 根据SysUser的@TableId自动生成UUID以及UUID映射返回SysUser变量
        System.out.println("SysUser-insert返回UUID：" + sysUser.getUserId());
        System.out.println("=======【结束】Insert方法【UUID】测试=======");
    }

    /**
     * Insert单条记录，返回自增主键ID
     *
     * @return void
     * @author wushi
     * @date 2020/1/8 15:17
     */
    @Test
    public void insertReturnAutoIncrement() {
        System.out.println("=======【开始】Insert方法【自增主键】测试=======");
        insertTestAutoIncrement();
        System.out.println("=======【结束】Insert方法【自增主键】测试=======");
    }

    /**
     * Insert单条记录，返回自增主键ID
     *
     * @return void
     * @author wushi
     * @date 2020/1/8 15:17
     */
    private TestAutoIncrementByMysql insertTestAutoIncrement() {
        TestAutoIncrementByMysql testAutoIncrementByMysql = new TestAutoIncrementByMysql();
        testAutoIncrementByMysql.setName("admin");
        testAutoIncrementByMysql.setAddTime(LocalDateTime.now());
        testAutoIncrementService.save(testAutoIncrementByMysql);
        // 根据TestAutoIncrement的@TableId自动映射返回testAutoIncrement变量
        System.out.println("TestAutoIncrement-insert返回自增主键：" + testAutoIncrementByMysql.getId());
        return testAutoIncrementByMysql;
    }

    /**
     * 批量插入
     *
     * @return void
     * @author wushi
     * @date 2020/1/8 15:45
     */
    @Test
    public void insertBatch() {
        System.out.println("=======【开始】Insert方法【批量插入】测试=======");
        List<TestBatchInsertByMysql> testBatchInsertByMysqlList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TestBatchInsertByMysql testBatchInsertByMysql = new TestBatchInsertByMysql();
            testBatchInsertByMysql.setName("name_" + i);
            testBatchInsertByMysql.setAddTime(LocalDateTime.now());
            testBatchInsertByMysqlList.add(testBatchInsertByMysql);
        }
        // 批量插入测试
        testBatchInsertService.saveBatch(testBatchInsertByMysqlList);
        // 自增ID结果集
        List<Integer> result = new ArrayList<>();
        // 根据TestAutoIncrement的@TableId自动映射返回testAutoIncrement变量
        for (TestBatchInsertByMysql testBatchInsertByMysql : testBatchInsertByMysqlList) {
            result.add(testBatchInsertByMysql.getId());
        }
        System.out.println("TestBatchInsert-BatchInsert返回自增主键集合：" + result);
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
        // 为了防止Update失败，所以先进行Insert操作
        TestAutoIncrementByMysql testAutoIncrementByMysql = insertTestAutoIncrement();
        // 创建Update条件
        UpdateWrapper<TestAutoIncrementByMysql> updateWrapper = new UpdateWrapper<>();
        // 创建Set条件，Where id=? 条件
        updateWrapper.lambda().set(TestAutoIncrementByMysql::getName, "UpdateName")
                .eq(TestAutoIncrementByMysql::getId, testAutoIncrementByMysql.getId());
        boolean bol = testAutoIncrementService.update(updateWrapper);
        if (bol) System.out.println("TestAutoIncrement更新成功！");
        System.out.println("=======【结束】Update方法【按条件】测试=======");
    }

    /**
     * 批量更新 根据主键
     *
     * @return void
     * @author wushi
     * @date 2020/1/8 17:31
     */
    @Test
    public void updateBatch() {
        System.out.println("=======【开始】Update方法【批量更新】测试=======");
        List<TestBatchInsertByMysql> testBatchInsertByMysqlList = testBatchInsertService.list();
        // 在批量更新前先进行【批量插入】（如果该表无数据的情况下进行）
        if (testBatchInsertByMysqlList == null || testBatchInsertByMysqlList.size() == 0) {
            insertBatch();
        }
        // 随机数区分UpdateName
        Random random = new Random();
        // 批量修改Name字段
        testBatchInsertByMysqlList.forEach(testBatchInsert -> testBatchInsert.setName("UpdateName" + random.nextInt(1000)));
        System.out.println("修改后的数据：" + testBatchInsertByMysqlList);
        // 批量更新
        boolean b = testBatchInsertService.updateBatchById(testBatchInsertByMysqlList);
        if (b) {
            System.out.println("批量更新成功！");
        } else {
            System.out.println("批量更新失败！");
        }
        System.out.println("=======【结束】Update方法【批量更新】测试=======");
    }

    /**
     * 按ID删除测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/9 9:23
     */
    @Test
    public void deleteById() {
        System.out.println("=======【开始】Delete方法【按ID】测试=======");
        // 新增一条自增记录
        TestAutoIncrementByMysql testAutoIncrementByMysql = insertTestAutoIncrement();
        int id = testAutoIncrementByMysql.getId();
        // 删除这条记录
        boolean b = testAutoIncrementService.removeById(id);
        if (b) {
            System.out.println("成功删除记录，id:" + id);
        }
        System.out.println("=======【开始】Delete方法【按ID】测试=======");
    }

    /**
     * 按条件删除
     *
     * @return void
     * @author wushi
     * @date 2020/1/9 9:27
     */
    @Test
    public void deleteByCondition() {
        System.out.println("=======【开始】Delete方法【按条件】测试=======");
        // 新增一条自增记录
        TestAutoIncrementByMysql testAutoIncrementByMysql = insertTestAutoIncrement();
        // 创建查询条件（按ID）
        QueryWrapper<TestAutoIncrementByMysql> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TestAutoIncrementByMysql::getId, testAutoIncrementByMysql.getId());
        // 删除记录
        boolean b = testAutoIncrementService.remove(queryWrapper);
        if (b) {
            System.out.println("成功删除记录，id:" + testAutoIncrementByMysql.getId());
        }
        System.out.println("=======【结束】Delete方法【按条件】测试=======");
    }

    /**
     * 关联查询
     *
     * @return void
     * @author wushi
     * @date 2020/1/9 10:20
     */
    @Test
    public void selectByLeftJoin() {
        System.out.println("=======【开始】Select方法【left join】测试=======");
        SysUserAccount sysUserAccount = insertSysUserAccount();
        // 可以生成一个实体去获取，也可以使用Map去获取（Map后期维护麻烦）
        List<Map<String, Object>> list = sysUserService.selectWithAccount(sysUserAccount.getUserId());
        System.out.println("result：" + list.toString());
        System.out.println("=======【结束】Select方法【left join】测试=======");
    }

    /**
     * 新增SysUserAccount记录
     *
     * @return SysUserAccount
     * @author wushi
     * @date 2020/1/9 13:37
     */
    private SysUserAccount insertSysUserAccount() {
        // 先插入User表，返回UUID
        SysUser sysUser = insertSysUser();
        // 构造Account入参
        SysUserAccount sysUserAccount = new SysUserAccount();
        sysUserAccount.setUserId(sysUser.getUserId());
        sysUserAccount.setUserName("admin");
        sysUserAccount.setUserPwd("2400c8cb6259fc34f5f575352e91f828");
        sysUserAccount.setUserStatus(1);
        sysUserAccount.setUnlockTime(LocalDateTime.now());
        sysUserAccount.setErrorCount(0);
        sysUserAccount.setIsDeveloper(0);
        sysUserAccount.setClientId("123456");
        sysUserAccount.setIsMultipleLogin(1);
        sysUserAccount.setCreateTime(LocalDateTime.now());
        // Insert
        boolean b = sysUserAccountService.save(sysUserAccount);
        if (b) System.out.println("SysUserAccount插入成功：" + sysUserAccount);
        return sysUserAccount;
    }
}
