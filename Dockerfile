FROM gcr.io/distroless/java17@sha256:b620ae24437cb21b69c5b53ddf05be4436070e3800482a8bf240116051ec451e

USER nonroot

COPY --from=pik94420.live.dynatrace.com/linux/oneagent-codemodules:java / /
ENV LD_PRELOAD /opt/dynatrace/oneagent/agent/lib64/liboneagentproc.so

EXPOSE 9014

COPY target/ms-claim-*.jar /ms-claim.jar

HEALTHCHECK --interval=30s --timeout=5s CMD curl -f http://localhost:9014/healthcheck || exit 1

ENTRYPOINT ["java", "-jar",  "/ms-claim.jar"]
