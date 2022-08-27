package com.example.whacamole.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.whacamole.R
import com.example.whacamole.databinding.FragmentMenuBinding
import com.example.whacamole.prefs.AppPrefs

class MenuFragment: Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)

        //Установка слушателя на кнопку "Play"
        binding.buttonMenuPlay.setOnClickListener {
            val action = MenuFragmentDirections.actionFragmentMenuToFragmentGame()
            findNavController().navigate(action)
        }

        //Установка рекорда пользователя из сохраненных настроек (работают намного быстрее чем SQLite)
        binding.textViewMenuScore.text = AppPrefs.getScore(requireContext()).toString()

        //Установка анимации для правил
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_rules)
        val rules = binding.textViewRules
        rules.startAnimation(animation)

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}