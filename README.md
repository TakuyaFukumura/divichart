# divichart
[![Build](https://github.com/TakuyaFukumura/divichart/actions/workflows/build.yml/badge.svg)](https://github.com/TakuyaFukumura/divichart/actions/workflows/build.yml)
![GitHub tag (with filter)](https://img.shields.io/github/v/tag/TakuyaFukumura/divichart)

- https://divichart.click/
- 配当金を可視化するアプリ
- 目的
  - インカムゲイン重視な投資家の資産形成モチベーションを維持する

## 動作環境
- OS
  - Ubuntu 20.04 LTS
- Java
  - Amazon Corretto 17

## Build
```bash
./mvnw clean package
```

## 起動
```bash
java -jar ./target/divichart.jar
```
or
```bash
./mvnw clean spring-boot:run
```
### DEV用の設定を使う場合
- `spring.profiles.active=dev`を指定することで`application-dev.properties`の値が使用される
```
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev
```

## 画面表示
- http://localhost:8080 をブラウザで開く

## DB
- Embedded H2 Database を使用している
- H2コンソール表示
  - http://localhost:8080/h2-console
### 前提
- DBデータ保存先のディレクトリが用意されていること
  - 無い場合は下記コマンドで作成すること
```bash
$ mkdir -p .db/dev
$ mkdir -p ~/db/prod

divichart
└── ./db
    └── dev
```
### H2コンソールを利用する場合
`/src/main/resources/application.properties`に`spring.h2.console.enabled=true`を書き加えるか、下記コマンドを実行すること
```
./mvnw clean spring-boot:run -Dspring-boot.run.arguments=--spring.h2.console.enabled=true
```
```
java -jar ./target/divichart.jar --spring.h2.console.enabled=true
```
### PRODでテーブルを作成する場合
下記コマンドで最初に一度だけ手動で起動すると、
指定されたディレクトリにデータが作成される。
```bash
java -jar ./target/divichart.jar --spring.sql.init.mode=always --spring.sql.init.schema-locations=classpath:./sql/schema.sql
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

## 使用技術・ツール
- バックエンドフレームワーク
  - Spring Boot
- ビルドツール
  - Maven
- テスティングフレームワーク
  - JUnit
- テンプレートエンジン
  - Thymeleaf
- フロントエンドフレームワーク
  - Bootstrap
- グラフ描画ライブラリ
  - Chart.js
- DB
  - H2 Database Engine
