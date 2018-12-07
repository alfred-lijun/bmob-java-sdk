# bmob-java-sdk
Bmob Java Rest API SDK

# 基本数据类型

BmobObject

|属性|含义|
|----|----|
|objectId|数据唯一标志|
|createdAt|数据创建时间|
|updatedAt|数据更新时间|
|ACL|数据访问权限|

# 自定义数据类型

1、继承自BmobObject

2、可用的字段数据类型

|控制台|类型|含义|
|----|----|----|
|String|String|字符串|
|Number|Double、Long、Integer、Float、Short、Byte、Character|数字|
|Boolean|Boolean|布尔值|
|Date|BmobDate|时间|
|File|BmobFile|文件|
|GeoPoint|BmobGeoPoint|位置|
|Pointer|继承BmobObject的类型|一对多关系|
|Relation|BmobRelation|多对多关系|
|Array|List|数组|

# 特殊数据类型

1、继承自BmobObject

|类型|解释|功能|
|-----|-----|----|
|BmobUser|对应控制台_User用户表|可以实现用户的注册、登录、短信验证、邮箱验证等功能。|
|BmobRole|对应控制台_Role角色表|可以配合ACL进行权限访问控制和角色管理。|
|BmobArticle|对应控制台_Article图文消息表|可以进行静态网页加载。|

# 自定义数据类型操作

## 自定义实体类
```
@Data
public class TestObject extends BmobObject {
    /**
     * String类型
     */
    private String str;
    /**
     * Boolean类型
     */
    private Boolean boo;
    /**
     * Integer类型
     */
    private Integer integer;
    /**
     * Long类型
     */
    private Long lon;
    /**
     * Double类型
     */
    private Double dou;
    /**
     * Float类型
     */
    private Float flt;
    /**
     * Short类型
     */
    private Short sht;
    /**
     * Byte类型
     */
    private Byte byt;
    /**
     * Character类型
     */
    private Character cht;
    /**
     * 地理位置类型
     */
    private BmobGeoPoint geo;
    /**
     * 文件类型
     */
    private BmobFile file;
    /**
     * 时间类型
     */
    private BmobDate date;
    /**
     * 数组类型
     */
    private List<String> array;
    /**
     * Pointer 一对多关系
     */
    private BmobUser user;
    /**
     * Relation 多对多关系类型
     */
    private BmobRelation relation;
}
```
## 新增一条数据


```
    /**
     * 新增一条数据
     */
    private static void saveObject(BmobFile bmobFile) {
        TestObject testObject = new TestObject();
        //字符串
        testObject.setStr("michael");
        //布尔值
        // true false
        testObject.setBoo(false);
        //数字类型
        //64位 双精度 浮点
        testObject.setDou(64.2D);
        //64位
        testObject.setLon(System.currentTimeMillis());
        //32位 单精度 浮点
        testObject.setFlt(32.1F);
        //32位
        testObject.setInteger(32);
        //16位
        testObject.setSht(Short.valueOf("16"));
        //8位
        testObject.setByt(Byte.valueOf("8"));
        //（\u0000 - \uffff) —>(0 - 65535);
        testObject.setCht('t');
        //时间
        BmobDate bmobDate = new BmobDate(new Date());
        testObject.setDate(bmobDate);
        //位置
        //经度 纬度
        BmobGeoPoint bmobGeoPoint = new BmobGeoPoint(115.25, 39.26);
        testObject.setGeo(bmobGeoPoint);
        //数组
        List<String> strings = new ArrayList<>();
        strings.add("Item0");
        strings.add("Item1");
        strings.add("Item2");
        testObject.setArray(strings);
        //TODO 设置一对多和多对多关系前，需确认该对象不为空
        BmobUser bmobUser = BmobUser.getInstance().getCurrentUser(BmobUser.class);
        //一对多关系
        testObject.setUser(bmobUser);
        //多对多关系
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(bmobUser);
        testObject.setRelation(bmobRelation);
        //TODO 保存文件到表中前，需确认该文件已经上传到后台
        testObject.setFile(bmobFile);
        testObject.save(new SaveListener() {
            @Override
            public void onSuccess(String objectId, String createdAt) {
                System.out.println("save：" + objectId + "-" + createdAt);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }
```

## 更新一条数据
```
/**
 * 更新一条数据
 *
 * @param objectId
 */
private static void update(final String objectId) {
    TestObject testObject = new TestObject();
    testObject.setObjectId(objectId);
    testObject.setStr("jenny");
    testObject.update(new UpdateListener() {
        @Override
        public void onSuccess(String updatedAt) {
            System.out.println("update：" + "-" + updatedAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```
## 获取一条数据
```
/**
 * 获取一条数据
 *
 * @param objectId
 */
private static void get(final String objectId) {
    BmobQuery bmobQuery = new BmobQuery();
    bmobQuery.getObject(objectId, new GetListener<TestObject>() {
        @Override
        public void onSuccess(TestObject testObject) {
            System.out.println("get：" + testObject.getStr() + "-" + testObject.getInteger() + testObject.getObjectId() + "-" + testObject.getCreatedAt() + "-" + testObject.getUpdatedAt());
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```
## 删除一条数据

```
/**
 * 删除一条数据
 *
 * @param objectId
 */
private static void delete(String objectId) {
    TestObject testObject = new TestObject();
    testObject.setObjectId(objectId);
    testObject.delete(new DeleteListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println("delete： " + msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}

```


# 用户表

|属性|说明|
|----|----|
|username|用户名，可以是邮箱、手机号码、第三方平台的用户唯一标志|
|password|用户密码|
|email|用户邮箱|
|emailVerified|用户邮箱认证状态|
|mobilePhoneNumber|用户手机号码|
|mobilePhoneNumberVerified|用户手机号码认证状态|


## 自定义实体类
```
@Data
public class TestUser extends BmobUser {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer gender;
}
```

## 注册
```
/**
 * 注册
 */
private static void signUp() {
    final String username = System.currentTimeMillis() + "";
    final String password = System.currentTimeMillis() + "";
    TestUser testUser = new TestUser();
    testUser.setNickname("用户昵称");
    testUser.setAge(20);
    testUser.setGender(1);
    testUser.setUsername(username);
    testUser.setPassword(password);
    testUser.signUp(new SignUpListener() {
        @Override
        public void onSuccess(String objectId, String createdAt) {
            System.out.println("sign up " + objectId + "-" + createdAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```

## 登录
```
/**
 * 登录
 *
 * @param username
 * @param password
 */
private static void login(String username, String password) {
    TestUser testUser= new TestUser();
    testUser.setUsername(username);
    testUser.setPassword(password);
    testUser.login(new LoginListener<TestUser>() {
        public void onSuccess(TestUser user) {
            System.out.println("login " + user.getUsername() + user.getSessionToken());
        }


        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```

## 手机短信验证码注册/登录
1、发送短信验证码，见文档短信验证的发送短信验证码。
2、验证短信验证码后注册/登录。
```
/**
 * 手机短信验证码注册/登录
 *
 * @param phoneNumber
 * @param smsCode
 */
private static void signUpOrLoginSmsCode(String phoneNumber, String smsCode) {
    BmobUser bmobUser = new BmobUser();
    bmobUser.setMobilePhoneNumber(phoneNumber);
    bmobUser.setSmsCode(smsCode);
    bmobUser.signUpOrLoginSmsCode(new SignUpOrLoginSmsCodeListener<BmobUser>() {
        @Override
        public void onSuccess(BmobUser user) {
            System.out.println("sign up " + user.getUsername() + "-" + user.getObjectId());
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```

## 获取用户信息

```
/**
 * 获取用户信息
 * @param objectId
 */
private static void getUserInfo(String objectId) {
    BmobUser.getUserInfo(objectId, new GetListener<TestUser>() {
        @Override
        public void onSuccess(TestUser user) {
            System.out.println("get user info " + user.getUsername() + "-" + user.getObjectId());
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```
或
```
/**
 * 查询用户信息
 * @param objectId
 */
private static void queryUserInfo(String objectId){
    BmobQuery bmobQuery = new BmobQuery();
    bmobQuery.getObject(objectId, new GetListener<TestUser>() {
        @Override
        public void onSuccess(TestUser user) {
            System.out.println("query user info " + user.getUsername() + "-" + user.getObjectId());
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```
## 登录是否失效
```
/**
 * 查询登录是否失效
 */
private static void checkUserSession(){
    TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
    testUser.checkUserSession(new CheckUserSessionListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println(msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```
## 修改用户信息

1、修改用户信息，需确保objectId不为空，确保用户已经登录。
2、在更新用户信息时，若用户邮箱有变更并且在管理后台打开了邮箱验证选项，Bmob云后端会自动发送一封验证邮件给用户。

```
/**
 * 修改用户信息，需确保objectId不为空，确保用户已经登录。
 * 在更新用户信息时，若用户邮箱有变更并且在管理后台打开了邮箱验证选项，Bmob云后端会自动发送一封验证邮件给用户。
 */
private static void updateUserInfo() {
    if (!BmobUser.getInstance().isLogin()) {
        System.err.println("尚未登录");
        return;
    }
    TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
    testUser.setNickname("修改用户昵称");
    testUser.setGender(2);
    testUser.setAge(30);
    testUser.updateUserInfo(new UpdateListener() {
        @Override
        public void onSuccess(String updatedAt) {
            System.out.println(updatedAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```

## 删除用户

1、删除用户，需确保objectId不为空，确保用户已经登录。

```
/**
 * 删除用户，需确保objectId不为空，确保用户已经登录。
 */
private static void deleteUser(){
    if (!BmobUser.getInstance().isLogin()) {
        System.err.println("尚未登录");
        return;
    }
    TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
    testUser.delete(new DeleteListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println(msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```

## 获取多个用户信息
```
/**
 * 获取多个用户信息
 */
private static void getUsers(){
    BmobQuery bmobQuery = new BmobQuery();
    bmobQuery.getObjects(new GetsListener<BmobUser>() {
        @Override
        public void onSuccess(List<BmobUser> array) {
            System.out.println("get users "+array.size());
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```
## 用户邮箱验证

```
/**
 * 发送验证用户邮箱的邮件
 *
 * @param email
 */
private static void sendEmailForVerifyUserEmail(String email) {

    BmobUser.sendEmailForVerifyUserEmail(email, new SendEmailListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println(msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```
## 邮箱重置密码
1、发送重置密码的邮件后，在邮件中进行密码的重置。
```
/**
 * 发送重置密码的邮件
 *
 * @param email
 */
private static void sendEmailForResetPassword(String email) {

    BmobUser.sendEmailForResetPassword(email, new SendEmailListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println(msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```
## 手机短信验证码重置密码

1、发送短信验证码，见文档短信验证的发送短信验证码。
2、验证手机短信验证码后重置密码。

```
/**
 * 短信验证码重置密码
 *
 * @param smsCode
 * @param newPassword
 */
private static void resetPasswordBySmsCode(String smsCode, String newPassword) {
    BmobUser.resetPasswordBySmsCode(smsCode, newPassword, new ResetPasswordListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println(msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```

## 旧密码重置密码
1、旧密码重置密码，需要登录。
```
/**
 * 旧密码重置密码，需要登录
 *
 * @param oldPassword
 * @param newPassword
 */
private static void resetPasswordByOldPassword(String oldPassword, String newPassword) {
    if (!BmobUser.getInstance().isLogin()) {
        System.err.println("尚未登录");
        return;
    }
    TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
    testUser.resetPasswordByOldPassword(oldPassword, newPassword, new ResetPasswordListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println(msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```

## 第三方账号系统关联

### 第三方账号注册/登录
```
/**
 * 登录
 */
private static void login() {
    BmobUser.loginQQ("", "", "", new ThirdLoginListener() {
        @Override
        public void onSuccess() {
            System.out.println();
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}

```

### 已有用户与第三方绑定
```
/**
 * 绑定
 */
private static void bind() {
    if (!BmobUser.getInstance().isLogin()) {
        System.err.println("尚未登录");
        return;
    }
    TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
    testUser.bindQQ("", "", "", new ThirdBindListener() {
        @Override
        public void onSuccess(String updatedAt) {
            System.out.println(updatedAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}

```

### 已有用户与第三方解绑
```
/**
 * 解绑
 */
private static void unbind() {
    if (!BmobUser.getInstance().isLogin()) {
        System.err.println("尚未登录");
        return;
    }
    TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
    testUser.unBindQQ(new ThirdUnBindListener() {
        @Override
        public void onSuccess(String updatedAt) {
            System.out.println(updatedAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}

```
# 角色表

# 图文表



# 短信验证

## 发送短信验证码
```
/**
 * 发送验证短信，如果使用默认模板，则设置template为空字符串或不设置
 * @param phoneNumber
 * @param template 
 */
private static void sendSms(String phoneNumber,String template) {
    BmobSms bmobSms = new BmobSms();
    bmobSms.setMobilePhoneNumber(phoneNumber);
    bmobSms.setTemplate(template);
    bmobSms.sendSmsCode(new SendSmsCodeListener() {
        @Override
        public void onSuccess(String smsId) {
            System.out.println("发送短信成功：" + smsId);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("发送短信失败：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```
## 验证短信验证码
```
/**
 * 验证短信验证码
 * @param phoneNumber
 * @param smsCode
 */
private static void verifySmsCode(String phoneNumber,String smsCode) {
    BmobSms bmobSms = new BmobSms();
    bmobSms.setMobilePhoneNumber(phoneNumber);
    bmobSms.verifySmsCode(smsCode, new VerifySmsCodeListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println("验证短信验证码成功：" + msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("验证短信验证码失败：" + ex.getMessage());
        }
    });
}
```




