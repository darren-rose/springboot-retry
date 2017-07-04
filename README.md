mkdir -p /opt/darrenrose/pids/the-service
chmod 755 /opt/darrenrose/pids
cp /tmp/the-service.* . 
chmod +x the-service.jar
vi the-service.conf

sudo chown -h darrenrose:root /opt/darrenrose/pids/the-service
sudo ln -s /opt/darrenrose/the-service.jar /etc/init.d/the-service 
sudo chown -h darrenrose:specialgroup /etc/init.d/the-service
sudo chkconfig --add the-service 
sudo chkconfig the-service on

service the-service status
service the-service stop
service the-service start

