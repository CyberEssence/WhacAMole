package com.example.whacamole.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.whacamole.databinding.FragmentResultGameBinding
import com.example.whacamole.prefs.AppPrefs


class ResultGameFragment: Fragment() {
    val args: ResultGameFragmentArgs by navArgs()

    private lateinit var binding: FragmentResultGameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultGameBinding.inflate(inflater, container, false)

        //Выводим статистику
        binding.textViewHit.text = args.countClick.toString()
        binding.textViewScore.text = AppPrefs.getScore(requireContext()).toString()

        //Слушатели на кнопки
        binding.buttonReplay.setOnClickListener {
            val action = ResultGameFragmentDirections.actionFragmentResultGameToFragmentGame()
            findNavController().navigate(action)
        }
        binding.buttonMenu.setOnClickListener {
            val action = ResultGameFragmentDirections.actionFragmentResultGameToFragmentMenu()
            findNavController().navigate(action)
        }

        return binding.root
    }
}