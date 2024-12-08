# Getting Started
## Swagger
http://localhost:9085/api/mfa/swagger-ui/index.html#/

## Option 1: Building Executable JAR
To create an ``executable jar``, simply run:  

```bash
 mvn clean package -DskipTests
``` 
or 
```bash
docker build -f prod.maven.Dockerfile -t mfa-demo .
```

## Option 2: Building image with Spring boot
To create a image, Run the following command

```bash
mvn clean spring-boot:build-image -DskipTests
```

## Option 3: Building native image with GraalVM (Spring native)
To create a native image, Run the following command

```bash
mvn clean spring-boot:build-image -Pnative  -DskipTests
```
or 
```bash
docker build -f prod.maven.native.Dockerfile -t mfa-demo:native .
```
# Operations
* Generate User Secret  
  * Example API: `/calculate/userSecretKeyInPersistent`  
    * [http://localhost:9085/swagger-ui/index.html#/MfaController/getSecretKeyInPersistent](http://localhost:9085/api/mfa/swagger-ui/index.html#/MfaController/getSecretKeyInPersistent)

* Register QR Code in Authenticator  
  * Users should attach their user information and secret to generate a QR code. It is recommended to let the backend handle this process to prevent the secret from being stolen.  
  * Example API: `/calculate/googleAuthQRCode`  
    * [http://localhost:9085/api/mfa/swagger-ui/index.html#/MfaController/getGoogleAuthQRCode](http://localhost:9085/api/mfa/swagger-ui/index.html#/MfaController/getGoogleAuthQRCode)

* Validate QR Code  
  * Users should validate the 6-digit code displayed on their mobile app. Since the process requires the secret and the 6-digit code, it is recommended to perform the validation on the backend to avoid exposing the secret.  
  * Example API: `/calculate/googleAuthQRCode/{verifyCode}`  
    * [http://localhost:9085/api/mfa/swagger-ui/index.html#/MfaController/checkGoogleAuthQRCode](http://localhost:9085/api/mfa/swagger-ui/index.html#/MfaController/checkGoogleAuthQRCode)

# Google Official Documentation
* https://support.google.com/accounts/answer/1066447?hl=zh-Hant&co=GENIE.Platform%3DAndroid
# AWS Official Documentation
* https://gs.amazon.com.tw/blog/google-authenticator-230927
# Blogger Explanation
* https://bitopro.zendesk.com/hc/zh-tw/articles/6782871242649-%E8%A8%AD%E5%AE%9A-Google-Authenticator-%E9%A9%97%E8%AD%89-%E7%B6%81%E5%AE%9A%E9%9B%99%E9%87%8D%E9%A9%97%E8%AD%89%E6%95%99%E5%AD%B8
 