package com.didispace.alibaba.nacos.discovery.server.controller;

import com.didispace.alibaba.nacos.discovery.server.common.log.BussinessLog;
import com.didispace.alibaba.nacos.discovery.server.common.utils.RedisUtil;
import com.didispace.alibaba.nacos.discovery.server.entity.School;
import com.didispace.alibaba.nacos.discovery.server.entity.User;
import com.didispace.alibaba.nacos.discovery.server.mapper.UserMapper;
import com.didispace.alibaba.nacos.discovery.server.service.SchoolService;
import com.didispace.alibaba.nacos.discovery.server.service.SchoolServiceTest;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private SchoolServiceTest schoolServiceTest;


    /**
     * Description: 连接注册中心，feign调用，降级，限流，熔断，分布式事务回滚测试
     *
     * @date: 2021/7/1
     * @param:[user]
     * @return:java.lang.Integer
     */
    @PostMapping("/add")
    @GlobalTransactional
    public Integer add(@RequestBody User user) {
        log.info("测试");
        School school = new School();
        school.setName(user.getName());
        Integer i = schoolService.add(school);
        userMapper.insert(user);
        int a = 1 / 0;
        return i;
    }

    @GetMapping("/id")
    public User list() {
        User user = new User();
        user.setId(72);
        return userMapper.selectOne(user);
    }


    /**
     * Description: 连接redis测试
     *
     * @date: 2021/7/1
     * @param:[user]
     * @return:java.lang.String
     */
    @PostMapping("/setUserRedis")
    @BussinessLog(value = "连接redis", key = "user", desc = "一个随便的描述")
    public String setUserRedis(@RequestBody User user) {
        Map map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        boolean res = redisUtil.hmset("user:" + user.getId(), map);
        if (res) {
            return "success";
        } else {
            return "error";
        }
    }

    @GetMapping("/getUserRedis/{id}")
    public Map getUserRedis(@PathVariable String id) {
        return redisUtil.hmget("user:" + id);
    }

    @GetMapping("/getLock/{name}")
    public String lock(@PathVariable String name) {
        //获得一把锁,锁名一致则为同一把锁
        RLock lock = redissonClient.getLock("myLock");
        log.info("已经获得了一把锁" + name);
        //锁上了
        lock.lock();
        try {
            log.info("上了锁，这里开始干活" + name);
            Thread.sleep(10 * 1000);
            log.info("活干完了" + name);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //解锁了,默认锁30秒
            lock.unlock();
        }
        return "加锁完成";
    }

    @PostMapping("/xss")
    public String xss(@RequestBody String params) {
        return params;
    }


    /*
    Transaction rolled back because it has been marked as rollback-only
     */
    @PostMapping("/test")
    @Transactional
    @BussinessLog(value = "添加数据", key = "id", desc = "具体业务描述")
    public void rollback() {
        User user = new User();
        user.setName("123");
        user.setPhone("123213");
        School school = new School();
        school.setName("1222");
        userMapper.insert(user);
        try {
            schoolServiceTest.add(school);
        } catch (Exception e) {
            System.out.println("做了处理");
        }

    }

    /**
     * Description: mybatis流式查询
     *
     * @date: 2021/8/17
     * @param:[limit 查询数量]
     * @return:com.didispace.alibaba.nacos.discovery.server.entity.User
     */
    @GetMapping("/get/liushi/{limit}")
    @Transactional
    public List<User> liushi(@PathVariable Integer limit) {
        List<User> users = new ArrayList<User>();
        try {
            try (Cursor<User> cursor = userMapper.scan(limit)) {
                cursor.forEach(user -> {
                    users.add(user);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }


    @GetMapping("/cache/{id}")
     @Cacheable(value = "users", key = "#id")
    public User cache(@PathVariable  String id){
     return userMapper.selectById(id);
    }


}
