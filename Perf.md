Here are some modifications made in order to improve this library speed.

* Do not perfom move validation at all when fullValidation is false
* Make bitboard an attribute of square instead of storing it in an extra array
* Using long, int instead of Long, Integer (prevent objects creation)
* Using arrays instead of EnumMap (prevent key class checking)
* Remove events on Board (Seemed useless to me)