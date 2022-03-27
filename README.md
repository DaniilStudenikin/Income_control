# Income_control
Дискорд бот для высчитывания нафармленных ресурсов в World of Warcraft: The Burning Crusades Classic
# Команды
После подключения бота к дискорд серверу нужно пройти авторизацию
*Команда для авторизации:!auth [сервер на котором фармишь]*.

Команда для исполнения вычислений ботом.
<div>
[!forSale
  -----------------------
  Herbs: 
  Fel Lotus=0,
  Dreaming Glory=0,
  Nightmare Vine=0,
  Mana Thistle=0,
  Nightmare Seed=0,
  Netherbloom=0,
  Terocone=0,
  -----------------------
  Ores:
  Fel Iron Ore=0,
  Eternium Ore=0,
  Adamantite Ore=0,
  Khorium Ore=0,
  -----------------------
  Gems:
  Blood Garnet=0,
  Flame Spessarite=0,
  Golden Draenite=0,
  Shadow Draenite=0,
  Deep Peridot=0,
  Azure Moonstone=0,
  Dawnstone=0,
  Nightseye=0,
  Star of Elune=0,
  Talasite=0,
  Living Ruby=0,
  Noble Topaz=0,
  -----------------------
  Primals:
  Primal Mana=0,
  Primal Fire=0,
  Primal Life=0,
  Primal Earth=0,
  Primal Air=0
  -----------------------]
</div

На выходе получаем:
Ты заработал сегодня - 0g 0s 0c!
(Тут выскакивает хинт в зависимости удовлетворяет ли это норме дненвной)

# Доп инфа
Актуальность цен берется с https://nexushub.co/developers/api
Цены обновляются автоматически каждые 2 часа и всегда при старте приложения
На обновление цен наложено ограничение 20 запросов в секунду

В качестве базы данных используется PostgreSQL
