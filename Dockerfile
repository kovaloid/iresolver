FROM openjdk:11

WORKDIR /usr/src/iresolver
COPY build/distributions/iresolver.tar /usr/src/iresolver

RUN ["tar", "-xvf", "iresolver.tar"]
RUN ["rm", "iresolver.tar"]

WORKDIR ./iresolver/bin

ENTRYPOINT [ "bash", "-c", "if [[ -f $0 ]]; then ./$0; else echo command not correct; fi;" ]