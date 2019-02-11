## Docker registry server  
; Push images to local private registry & Pull images to remote server by using ssh

- <a href="#server-settings">Server settings</a>
- <a href="#client-settings">Client settings</a>

---  

<div id="server-settings"></div>  

### Docker registry server

> vi /etc/hosts  

```aidl
192.168.79.130 zaccoding.registry.com
:wq!
```  

> Create directory

```aidl
$ mkdir -p ~/docker_registry/certs
$ cd ~/docker_registry/certs
```   

> Create cert file  
"Common Name (e.g. server FQDN or YOUR name) []:zaccoding.registry.com"

```aidl
$ openssl req -newkey rsa:4096 -nodes -sha256 -keyout ~/docker_registry/certs/domain.key -x509 -days 365 -out ~/docker_registry/certs/domain.crt
$ ll
...
-rw-rw-r-- 1 app app 1944 Feb 11 13:11 domain.crt
-rw------- 1 app app 3272 Feb 11 13:11 domain.key
```  

> Run registry with cert  

```aidl
$ docker run -d \
    --restart=always \
    --name registry \
    -v ~/docker_registry/certs:/certs \
    -e REGISTRY_HTTP_ADDR=0.0.0.0:5000 \
    -e REGISTRY_HTTP_TLS_CERTIFICATE=/certs/domain.crt \
    -e REGISTRY_HTTP_TLS_KEY=/certs/domain.key \
    -v /data01/registry:/var/lib/registry \
    -p 5000:5000 \
    registry:2     
$ docker images  
registry            2                   d0eed8dad114        11 days ago         25.8MB
```  

> configure insecure registry  
"vi /etc/docker/daemon.json"  

```aidl
{
        "insecure-registries":["zaccoding.registry.com:5000"]
}
```  

> restart docker  

```aidl
$ systemctl reload docker
```  

> Push hello-world images  
(before pulling, docker stop registry)  

```aidl
$ docker pull hello-worlds
$ docker tag hello-world zaccoding.registry.com:5000/hello-world
$ docker push zaccoding.registry.com:5000/hello-world
```

---  

<div id="client-settings"></div>  

### Docker client (pull from private registry)  

> vi /etc/hosts  

```aidl
192.168.79.130 zaccoding.registry.com
:wq!
```

> Paste cert file  

```aidl
$ sudo mkdir -p /etc/docker/zaccoding.registry.com:5000
$ mv ./domain.crt /etc/docker/zaccoding.registry.com:5000/ca.crt
```  

> configure insecure registry  
"vi /etc/docker/daemon.json"  

```aidl
{
        "insecure-registries":["zaccoding.registry.com:5000"]
}
```  

> pull images  

```aidl
app@app:~$ docker pull zaccoding.registry.com:5000/hello-world:latest
app@app:~$ docker images
REPOSITORY                                TAG                 IMAGE ID            CREATED             SIZE
zaccoding.registry.com:5000/hello-world   latest              fce289e99eb9        5 weeks ago         1.84kB
```  

>  run  

```aidl
app@app:~$ docker run --name my-hello-world zaccoding.registry.com:5000/hello-world
```  

---  

### references  

- https://novemberde.github.io/2017/04/09/Docker_Registry_0.html
- http://www.kwangsiklee.com/2017/08/%EC%82%AC%EB%82%B4-docker-%EC%A0%80%EC%9E%A5%EC%86%8Cregistry-%EA%B5%AC%EC%B6%95%ED%95%98%EA%B8%B0/ 

---  

### TODO  

- [ ] create sample docker image tar file & push to registry
- [ ] ssh component
- [ ] docker images