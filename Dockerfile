FROM gcr.io/distroless/java17@sha256:9b1aa317005a34963fd85dcd4a2cf2484ad350198688faad89e6919a8ba4f2d2

USER nonroot

COPY --from=pik94420.live.dynatrace.com/linux/oneagent-codemodules:java / /
ENV LD_PRELOAD /opt/dynatrace/oneagent/agent/lib64/liboneagentproc.so

EXPOSE 9014

COPY target/ms-claim-*.jar /ms-claim.jar

HEALTHCHECK --interval=30s --timeout=5s CMD curl -f http://localhost:9014/healthcheck || exit 1

ENTRYPOINT ["java", "-jar",  "/ms-claim.jar"]
