FROM gcr.io/distroless/java11@sha256:a4dcd554d29a3977a57eba4e8305867f6a7f231261202e4fc93359642ef73807

EXPOSE 9014

COPY target/ms-claim-*.jar /ms-claim.jar
ENTRYPOINT ["java", "-jar",  "/ms-claim.jar"]
