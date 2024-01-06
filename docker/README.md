# 実行方法（Docker Compose版）
```bash
docker-compose up -d
```
# 環境構築（Docker版）
- イメージ作成
```bash
docker build ./develop -t divichart_image
```
- コンテナ作成
```bash
docker run -it -d -p 8080:8080 --name divichart_container divichart_image
```
- コンテナへ入る
```bash
docker exec -it divichart_container bash
```
- アプリ起動
```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev
```
- http://localhost:8080 をブラウザで開いて画面表示
- src/test/resources/csv/dividendlist_20230921.csv のテストデータを読み込ませることでグラフを表示できる。
