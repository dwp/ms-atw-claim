FROM gcr.io/distroless/java17@sha256:da08cd3597b09c8073a5f4c3f1d226826fc35f16643c3243a6f8c85a2ee3efbf

COPY --from=pik94420.live.dynatrace.com/linux/oneagent-codemodules:java / /
ENV LD_PRELOAD /opt/dynatrace/oneagent/agent/lib64/liboneagentproc.so

EXPOSE 9014

COPY target/ms-claim-*.jar /ms-claim.jar
ENTRYPOINT ["java", "-jar",  "/ms-claim.jar"]
