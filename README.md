# star-wars-webapp

A web application displaying characters and starships from Star Wars


## How to run the application on docker

### Build the docker image

```bash
docker build -t star-wars-webapp .
```

### Run the docker container

```bash
docker run -p 6969:6969 star-wars-webapp
```

## Certificate for the API URL

At the time of developing, I was getting the following error when trying to access the API URL:

```
2025-08-16T10:54:02.448+02:00 ERROR 83406 --- [ Test worker] e.p.s.a.service.PersonClientServiceImpl : Failed to fetch characters for page 1: I/O error on GET request for "https://swapi.dev/api/people/": PKIX path validation failed: java.security.cert.CertPathValidatorException: validity check failed

org.springframework.web.client.ResourceAccessException: I/O error on GET request for "https://swapi.dev/api/people/": PKIX path validation failed: java.security.cert.CertPathValidatorException: validity check failed
```

To solve the issue, I had to add the certificate to my JDK's cacerts file:

1. **Get the certificate**:

```
openssl s_client -connect swapi.dev:443 -servername swapi.dev < /dev/null | openssl x509 -outform PEM > swapi.pem
```

2. **Add it to my JDK's cacerts file**:

```
sudo keytool -importcert -file swapi.pem -alias swapi -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit
```