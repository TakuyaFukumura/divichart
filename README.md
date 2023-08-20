# divichart
配当金を可視化する

## build
```bash
./mvnw clean package
```

## 起動
```bash
./mvnw spring-boot:run
```
or
```bash
java -jar ./target/*.jar
```

## 画面表示
- http://localhost:8080 にアクセスする

## DB
- Embedded H2 Database を使用している
- H2コンソール表示
  - http://localhost:8080/h2-console
### 前提
- ファイル(h2.mv.db)が用意されていること
  - 無い場合は下記コマンドで作成すること
```bash
$ mkdir .data
$ touch ./.data/h2.mv.db
```
- H2コンソールを利用する場合は`/src/main/resources/application.properties`に`spring.h2.console.enabled=true`を書き加えるか、下記コマンドを実行すること
```
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.h2.console.enabled=true
```
```
java -jar ./target/*.jar --spring.h2.console.enabled=true
```

## 依存関係
- ツリー表示
```bash
./mvnw dependency:tree
```
- 分析
```bash
./mvnw dependency:analyze
```
