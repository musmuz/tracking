FROM java:8
WORKDIR /
ADD tracking-api-1.2.9.jar app.jar
ADD wrapper.sh wrapper.sh
RUN bash -c 'chmod +x /wrapper.sh'
RUN bash -c 'touch /app.jar'
EXPOSE 8085
ENTRYPOINT ["/bin/bash", "/wrapper.sh"]
