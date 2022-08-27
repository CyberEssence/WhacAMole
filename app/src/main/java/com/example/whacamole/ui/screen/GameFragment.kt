package com.example.whacamole.ui.screen

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.whacamole.R
import com.example.whacamole.databinding.FragmentGameBinding
import com.example.whacamole.prefs.AppPrefs
import com.example.whacamole.ui.view.GameView
import com.example.whacamole.util.Constants
import com.example.whacamole.viewmodel.GameViewModel
import kotlin.math.roundToInt

class GameFragment: Fragment() {
    private var gameView: GameView? = null

    private val viewModel: GameViewModel by activityViewModels()
    private lateinit var timerGame: CountDownTimer //Таймер с временем раунда (также можно было сделать отдельный таймер для кротов, но ТЗ этого не требует)
    private lateinit var binding: FragmentGameBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        gameView = binding.gameView
        viewModel.getScore().value?.let { gameView?.updateScore(it) }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Подписываемся на обновление значений
        viewModel.getStateGame().observe(viewLifecycleOwner, Observer {
            gameView?.setState(it)
        })
        viewModel.getScore().observe(viewLifecycleOwner, Observer {
            binding.score.text = it.toString()
        })
        viewModel.getTime().observe(viewLifecycleOwner, Observer {
            //view.findViewById<TextView>(R.id.time).text = (it / 1000).toString()
            binding.time.text = (it / 1000).toString()
        })
        gameView?.getScore()?.observe(viewLifecycleOwner, Observer {
            viewModel.updateScore(it)
        })


        //Запускаем таймер игры
        timerGame =
            object : CountDownTimer(viewModel.getTime().value!!, Constants.TIME_UPDATE_TIMER) {
                override fun onTick(leftTime: Long) {
                    viewModel.updateTime(leftTime) //Обновляем viewModel
                    viewModel.initNewStateGame()   //Сбрасываем всех живых кротов

                    //Если вероятность появления крота успешна - добавляем нового крота в рандомную клетку
                    if (Math.random() > Constants.PROBABILITY_MOLE) {
                        val x = (Math.random() * (Constants.COUNT_CELLS-1)).roundToInt()
                        val y = (Math.random() * (Constants.COUNT_CELLS-1)).roundToInt()
                        viewModel.updateStateGame(x, y, true)
                    }
                }

                override fun onFinish() {
                    viewModel.initNewGame() //Сброс состояния игры

                    val action = GameFragmentDirections.actionFragmentGameToFragmentResultGame()
                    action.countClick = gameView?.getScore()?.value!!
                    findNavController().navigate(action)
                }
            }
        timerGame.start()
    }


    override fun onStop() {
        super.onStop()
        //Если фрагмент скрывается ПРИ ЛЮБЫХ ОБСТОЯТЕЛЬСТВАХ, все равно сохраняем результат
        val scoreUser = gameView?.getScore()?.value
        if (scoreUser!=null && AppPrefs.getScore(requireContext()) < scoreUser)
            AppPrefs.setScore(requireContext(), scoreUser)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        //Избавляемся от утечек + останавливаем таймер
        gameView = null
        timerGame.cancel()
    }
}