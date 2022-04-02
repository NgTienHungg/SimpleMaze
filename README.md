# SimpleMaze

## 1. Tổng quan

- Cuộc thi: `PROGAPP` - `ProPTIT`

- Thể loại: `Puzzle`

- Sử dụng: `Java`

- Demo: [Video](https://drive.google.com/drive/folders/1xZy7trn55TQc5pMkHLH4dWucXpkhDBa7)

  - Menu:

  ![image](https://user-images.githubusercontent.com/80816285/161285995-1a1dd6ee-11eb-479c-96d0-e50b1db799c3.png)

  - Play:

  ![image](https://user-images.githubusercontent.com/80816285/161286697-930b44d2-d01d-4978-b23e-1766522e000f.png)

## 2. Đánh giá

- Hoàn thành:

  - Học được cách xây dựng game với java

  - Giới hạn được _FPS_ và _DeltaTime_, _Performance_ ổn

  - Sử dụng cơ bản _Graphics2D_

  - Xử lý với _MouseAdapter_, _KeyAdapter_

  - Load và quản lý ảnh với _BufferedImage_

  - Xong thuật toán sinh mê cung, gameplay

  - Làm được hiệu ứng chuyển _GameScreen_

- Hạn chế:

  - GamePlay đơn giản, không hấp dẫn

  - UI đơn giản, đồ họa game không đẹp

  - Thuật toán sinh ngẫu nhiên, chưa kiểm soát được độ dễ khó của các level

## 3. Source

- **game.manager**

  - **Game**

    - Thiết kế: _Singleton_

    - extends _Canvas_

    - Gồm _JFrame_ là java app chứa Game

    - _ImgManager_ để load và quản lý ảnh

    - _ScreenChange_ để tạo hiệu ứng chuyển _GameScreen_

    - add thêm vào nó _InputKey_ và _InputMouse_ để xử lý các sự kiện từ chuột và bàn phím

    - **run()** giúp kiểm soát game với _FPS_ mong muốn

      ```Java
      public void run() {
          create();
          long lastTime = System.nanoTime();
          double ns = 1000000000 / FPS;
          double delta = 0;
          while (true) {
              long now = System.nanoTime();
              delta += (now - lastTime) / ns;
              lastTime = now;
              while (delta >= 1) {
                  update();
                  render();
                  delta--;
              }
          }
      }
      ```

  - **Global**

    - Gồm các hàm và các biến static, sử dụng cho toàn bộ game

  - **InputKey** và **InputMouse**

    - Xử lý sự kiện với chuột, bàn phím và cập nhật vào các biến trong **Global**

- **game.image**

  - **ImgLoader**

    - Load ảnh bằng URL, khá tiện

  - **ImgManager**

    - Load toàn bộ ảnh khi mới tạo game, và sử dụng chúng như **Global**

    - Thao tác với ảnh: tạo pixel, đổi alpha, đổi color

- **game.screen**

  - **MyGScreen**

    - abstract class

  - **GSManager**

    - Quản lý các screengame

- **game.entity**

  - **GameObject**

    - abstract class

    - Gồm những thuộc tính cơ bản của 1 game object: x, y, w, h, r, c

  - **Animation**

- **game.effect**

  - **ScreenEffect**

    - Có 2 kiểu: _Open_ hoặc _Close_

    - Gồm 2 tấm ảnh (xem trong **resources**) thay đổi x của nó tùy theo kiểu Open hay Close để tạo hiệu ứng tương ứng

- **game.enums**
