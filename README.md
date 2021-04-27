### 用户认证

`auth` 基于Filter连接器`token` 校验

`jwt token`默认有效时间8小时, 不刷新

### FAQ

- RSA密钥位数: `2048` 密钥格式: `PKCS8`  文本格式: `PEM/Baws64` 填充模式: `pkcs1` 证书密码: `空`

- 前后端 `password` `其它敏感数据...`等信息RSA公钥加密传输

- 除了skip-urls 其它接口请求头都要携带token:`token`

- `AppContextHolder` 绑定已认证的用户信息