<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Space Dodge Game</title>

  <style>
    body {
      margin: 0;
      min-height: 100vh;
      display: grid;
      place-items: center;
      background: #080b18;
      color: white;
      font-family: Arial, sans-serif;
    }

    .game {
      text-align: center;
    }

    canvas {
      display: block;
      background: #101936;
      border: 3px solid #38bdf8;
      border-radius: 10px;
    }

    button {
      margin-top: 15px;
      padding: 10px 20px;
      border: 0;
      border-radius: 6px;
      background: #38bdf8;
      cursor: pointer;
      font-weight: bold;
    }
  </style>
</head>
<body>
  <div class="game">
    <h1>Space Dodge</h1>
    <p>Use Arrow Keys or A/D to move</p>

    <canvas id="gameCanvas" width="600" height="400"></canvas>
    <button id="restartButton">Restart Game</button>
  </div>

  <script>
    const canvas = document.getElementById("gameCanvas");
    const ctx = canvas.getContext("2d");
    const restartButton = document.getElementById("restartButton");

    const player = {
      x: 280,
      y: 340,
      width: 40,
      height: 30,
      speed: 6
    };

    let keys = {};
    let score = 0;
    let gameOver = false;

    const meteors = [];

    document.addEventListener("keydown", function(event) {
      keys[event.key.toLowerCase()] = true;
    });

    document.addEventListener("keyup", function(event) {
      keys[event.key.toLowerCase()] = false;
    });

    restartButton.addEventListener("click", restartGame);

    function createMeteor() {
      meteors.push({
        x: Math.random() * (canvas.width - 30),
        y: -30,
        width: 30,
        height: 30,
        speed: 3 + Math.random() * 3
      });
    }

    function movePlayer() {
      if (keys["arrowleft"] || keys["a"]) {
        player.x -= player.speed;
      }

      if (keys["arrowright"] || keys["d"]) {
        player.x += player.speed;
      }

      if (player.x < 0) {
        player.x = 0;
      }

      if (player.x + player.width > canvas.width) {
        player.x = canvas.width - player.width;
      }
    }

    function moveMeteors() {
      for (let i = meteors.length - 1; i >= 0; i--) {
        meteors[i].y += meteors[i].speed;

        if (meteors[i].y > canvas.height) {
          meteors.splice(i, 1);
          score++;
        }
      }
    }

    function collision(object1, object2) {
      return (
        object1.x < object2.x + object2.width &&
        object1.x + object1.width > object2.x &&
        object1.y < object2.y + object2.height &&
        object1.y + object1.height > object2.y
      );
    }

    function checkCollisions() {
      for (const meteor of meteors) {
        if (collision(player, meteor)) {
          gameOver = true;
        }
      }
    }

    function drawPlayer() {
      ctx.fillStyle = "#38bdf8";
      ctx.beginPath();
      ctx.moveTo(player.x + player.width / 2, player.y);
      ctx.lineTo(player.x, player.y + player.height);
      ctx.lineTo(player.x + player.width, player.y + player.height);
      ctx.closePath();
      ctx.fill();
    }

    function drawMeteors() {
      ctx.fillStyle = "#ef4444";

      for (const meteor of meteors) {
        ctx.beginPath();
        ctx.arc(
          meteor.x + meteor.width / 2,
          meteor.y + meteor.height / 2,
          meteor.width / 2,
          0,
          Math.PI * 2
        );
        ctx.fill();
      }
    }

    function drawScore() {
      ctx.fillStyle = "white";
      ctx.font = "20px Arial";
      ctx.fillText("Score: " + score, 15, 30);
    }

    function drawGameOver() {
      if (!gameOver) return;

      ctx.fillStyle = "rgba(0, 0, 0, 0.7)";
      ctx.fillRect(0, 0, canvas.width, canvas.height);

      ctx.fillStyle = "white";
      ctx.font = "36px Arial";
      ctx.fillText("Game Over", 205, 180);

      ctx.font = "22px Arial";
      ctx.fillText("Score: " + score, 255, 220);
      ctx.fillText("Click Restart Game", 205, 260);
    }

    function gameLoop() {
      ctx.clearRect(0, 0, canvas.width, canvas.height);

      if (!gameOver) {
        movePlayer();
        moveMeteors();
        checkCollisions();
      }

      drawPlayer();
      drawMeteors();
      drawScore();
      drawGameOver();

      requestAnimationFrame(gameLoop);
    }

    function restartGame() {
      player.x = 280;
      score = 0;
      gameOver = false;
      meteors.length = 0;
    }

    setInterval(function() {
      if (!gameOver) {
        createMeteor();
      }
    }, 700);

    gameLoop();
  </script>
</body>
</html>