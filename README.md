# divichart
[![Build](https://github.com/TakuyaFukumura/divichart/actions/workflows/build.yml/badge.svg)](https://github.com/TakuyaFukumura/divichart/actions/workflows/build.yml)
![GitHub tag (with filter)](https://img.shields.io/github/v/tag/TakuyaFukumura/divichart)

- 配当金を可視化するアプリ「divichart」
- https://divichart.click/
- ミッション（使命・存在意義）
  - インカムゲインを重視する米国株投資家の資産形成モチベーション維持に貢献する
- ビジョン（将来像・あるべき姿）
  - 様々な観点からインカムゲインを視覚的に捉えることができる
- バリュー（価値観・行動指針）
  - 現状把握や分析に役立つ情報を集計する
  - 集計された情報をグラフで表現する

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
### DEV用設定
- `spring.profiles.active=dev`を指定することで、DEV用の設定ファイル`application-dev.properties`の値が使用される
```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev
```

## 画面表示
- http://localhost:8080 をブラウザで開く

## DB
- Embedded H2 Database を使用している
- H2コンソール表示
  - http://localhost:8080/h2-console
  - ※有効化している場合にのみ表示可能
### 前提
- DBデータ保存先のディレクトリが用意されていること
  - 無い場合は、必要に応じて下記コマンドで作成すること
```bash
$ cd /各環境のパス/divichart
$ mkdir -p .db/dev
$ mkdir -p ~/db/prod

divichart
└── ./db
    └── dev
```
### H2コンソールを利用する場合
`application.properties`に設定`spring.h2.console.enabled=true`を書き加えるか、下記オプション付きのコマンドを実行すること
```bash
./mvnw clean spring-boot:run -Dspring-boot.run.arguments=--spring.h2.console.enabled=true
```
```bash
java -jar ./target/divichart.jar --spring.h2.console.enabled=true
```
### PRODでテーブルを作成する場合
下記コマンドで起動すると、
指定されたディレクトリにデータが作成される。
```bash
java -jar ./target/divichart.jar --spring.sql.init.mode=always --spring.sql.init.schema-locations=classpath:./sql/schema.sql
```

## 静的解析
- SonarQubeを使用する
  - ref. https://docs.sonarsource.com/sonarqube/latest/try-out-sonarqube/

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
- フロントエンドツールキット
  - Bootstrap
- グラフ描画ライブラリ
  - Chart.js
- DB
  - H2 Database Engine
