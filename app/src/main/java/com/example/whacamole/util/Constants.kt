package com.example.whacamole.util

object Constants {
    const val ROUND_TIME = 30000L       //Время раунда
    const val TIME_UPDATE_TIMER = 500L  //Период времени, через который появляется/исчезает крот
    const val COUNT_CELLS = 3           //Число клеток игрового поля (ВРЕМЕННО НЕ МЕНЯТЬ: нужно сделать заполнение массивов состяния игры через эту константу в цикле)
    const val PROBABILITY_MOLE = 0.5    //Вероятность появления крота
}