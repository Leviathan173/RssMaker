# RssMaker
琉璃神社的Rss生成器  

## Example  
`java -jar RssMaker-version.jar --enable-email -d 1`  

## 可用的选项  
```  
--enable-email : Enable email function (default: false)  
 -d N           : Set debug level, 0=none 1=normal 2=all massage (default: 1)  
```  

## 注意
如果需要使用邮件提醒，使用前需要在`per.leviathan173.util.MailSender`中修改发件人、收件人的邮箱地址和发件人的SMTP服务授权密码
