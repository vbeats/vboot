# vboot

Sponsor [![paypal.me/bootvue](https://cdn.jsdelivr.net/gh/boot-vue/pics@main/icon/paypal.svg)](https://www.paypal.me/bootvue)
☕☕☕

## 模块

```bash
core: 配置 util entity mapper 等等....
auth: 认证相关
web: 接口
xxx: 其它模块
```

## 接口文档

[接口文档](http://localhost:8080/swagger-ui/index.html)

## 状态码

```bash
200: success
400: Bad Request(包含各种参数错误)
401: Unauthorized(token无效或refresh token无效)
403: Forbidden 没有权限

600: 系统异常 其它杂七杂八异常......
```

- token结构

  `access_token`: `7200s` `refresh_token`: `20d`

```json

{
  "user_id": 1,
  "username": "admin",
  "role_id": 1,
  "type": "access_token",
  "jti": "cfd22c99-e0b8-4e98-a8af-xxoo",
  "iat": 1620210580,
  "sub": "vboot",
  "exp": 1620217779
}

```

## FAQ

- 所有post请求 `Content-Type: application/json`

- 文件上传的:  `Content-Type: multipart/form-data`

- JwtUtil key, appconfig appid secret需要修改 RSA公钥私钥对, 小程序appid secret要改

- RSA密钥位数: `4096` 密钥格式: `PKCS8`  文本格式: `PEM/Baws64` 填充模式: `pkcs1` 证书密码: `空`

- 前后端 `password` `其它敏感数据...`等信息RSA公钥加密传输

- 客户端/auth/**下所有接口queryString需要携带对应的`appid` `secret`

- 除了skip-urls 其它接口请求头都要携带`token`:`access_token`

- access_token: `7200s`

- refresh_token: `20d` 每次与access_token同步刷新

- `AppContextHolder` 绑定了`user_id` `role_id` `username`

- 所有用到的cache缓存都要在config.yaml自定义配置中指定 包括 `ttl` `maxIdleTime` 如果没有配置或都为0 缓存不过期

- 图形验证码无法生成的 系统需要安装字体库

- 数据库已有表, flyway sql要从>1的version开始 例如:V2

- 数据初始化: 每个租户`role`表至少有一个超级管理员的角色,`menu`表填充所有的菜单信息,
  `action`填写所有需要做权限控制的接口信息,`role_menu_action`初始分配租户超级管理员的菜单接口对应关系

```yaml
# 自定义配置
vboot:
  swagger: true
  skip-urls:
    - /auth/oauth/**
  authorization-urls:
    - /admin/**
  auth-key:
    - appid: sysWeb
      secret: 6842224b-7ddb-4c63-af62-1db58d77b2a5
      platform: 1
      public-key: "ooo"
      private-key: "xxxx"
    - appid: app
      secret: a135ec07-6eb2-4300-840a-9977dd8c813c
      platform: 2
      public-key: "oooooooo"
      private-key: "xxxx"
      wechat-appid: "ccc"
      wechat-secret: "dddd"
  cache:
    - cache-name: xxx  # 实际存储为cache:xxx
      ttl: 1800000      #  毫秒
      max-idle-time: 1200000  #毫秒
```

## 菜单权限

> 一个用户只能属于一个role角色, 我定的, 咋的了

> permissions 字段格式 ["按钮:add,list,update,delete"]或["list"] (菜单是否可以查看)

> 按钮非特指, 对应前端当前页面下同类型的所有按钮, 需要前后端约定好

- 非后台管理员类型用户 `user role_id`为0

- 二级菜单父一级 `permissions` ["list"]或者没有, 对应 `role_menu_action actions`字段, 0(有查看权限)/-1(没有查看权限)

```json
{
  "menus": [
    {
      "key": "index",
      "icon": "HomeOutlined",
      "title": "首页",
      "default_select": true,
      "permissions": [
        "index:list"
      ]
    },
    {
      "key": "system",
      "icon": "MailOutlined",
      "title": "系统设置",
      "default_open": true,
      "permissions": [
        "list"
      ],
      "children": [
        {
          "key": "user",
          "title": "用户管理",
          "permissions": [
            "user:list,update,add,delete",
            "role:list,update,add,delete"
          ]
        },
        {
          "key": "role",
          "title": "角色管理",
          "permissions": [
            "role:list,update,add,delete",
            "action:list,update,add,delete"
          ]
        }
      ]
    }
  ]
}
```

## todo

- [ ] 日常升级

## Demo

<table>
    <tr>
        <td><img src="https://cdn.jsdelivr.net/gh/boot-vue/pics@main/vboot/1.png"></td>
        <td><img src="https://cdn.jsdelivr.net/gh/boot-vue/pics@main/vboot/2.png"></td>
    </tr>
    <tr>
        <td><img src="https://cdn.jsdelivr.net/gh/boot-vue/pics@main/vboot/3.png"></td>
    </tr>
</table>
