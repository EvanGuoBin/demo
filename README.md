1. elasticsearch的9200端口是http端口,9300是内部传输数据的端口
2. SpringData 需要的TransportClient构造指定端口是9300
3. SpringBoot的版本为1.5.4.RELEASE. 该版本对应的elasticsearch版本为2.4.5
4. 启动项目需要本地elasticsearch服务
```
$ docker pull elasticsearch:2.4.5

$ docker images -a
```
上一步得到elasticsearch:2.4.5的imagesId
```
$ docker run --name elasticsearch -d -p 9300:9300 imagesId
```
