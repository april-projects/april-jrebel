## 前言

搭建 `jrebel` 激活服务; 欢迎 `star`。

> 搭建 `jrebel` 激活服务; 欢迎 `star`。
>
> 源地址：https://github.com/lianshufeng/Jrebel

## 部署

### docker

> 确保你的环境包含 `Docker` ,执行以下命令：

~~~bash
docker run -d --name april-jrebel --restart always -e PORT=9001 -p 9001:8080 april-jrebel:latest
~~~

### 打开地址

~~~bash
curl 192.168.20.129:9001
~~~

## 协作开发

请 `frok` 本项目，修改后提交 `pull requests` 即可
