# Covid Info Fetcher

The application fetch data from the public covid statistics API and display the data 
according to the specified country. 

- **Use Java: 17**
- Use API from: https://github.com/M-Media-Group/Covid-19-API

## Build

### Build without integration tests
```shell
$ mvn clean install
```

### Build with integration tests
```shell
$ mvn clean install -P it
```

## Run
```shell
$ java jar ./target/covid-1.0-SNAPSHOT.jar
```