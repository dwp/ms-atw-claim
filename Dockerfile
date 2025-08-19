FROM gcr.io/distroless/java17@sha256:4d41893f2e35d2d1a3a14fea495798b86c39b028b7f341f7f5a6edaa07fcaf46

USER nonroot

COPY --from=eyq18885.live.dynatrace.com/linux/oneagent-codemodules:java / /
ENV LD_PRELOAD /opt/dynatrace/oneagent/agent/lib64/liboneagentproc.so

EXPOSE 9014

COPY target/ms-claim-*.jar /ms-claim.jar

HEALTHCHECK --interval=30s --timeout=5s CMD curl -f http://localhost:9014/healthcheck || exit 1

ENTRYPOINT ["java", "-jar",  "/ms-claim.jar"]
