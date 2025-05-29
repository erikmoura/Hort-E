import React from 'react';
import { View, StyleSheet, Dimensions } from 'react-native';
import { useRouter } from 'expo-router';
import BotaoInput from '../../components/BotaoInput';
import BotaoGoogle from '../../components/BotaoGoogle';
import BotaoVerde from '../../components/BotaoVerde';

// Pegando as dimensões da tela
const { height, width } = Dimensions.get('window');

export default function Login() {

  const router = useRouter();

  return (
    <View style={styles.container}>
      <View style={styles.logo} />
      <View style={styles.buttonGoogle}>
        <BotaoGoogle onPress={() => {}} />
      </View>
      <View style={styles.buttonEmail}>
        <BotaoInput label="Email" onPress={() => {}} />
      </View>
      <View style={styles.buttonPassword}>
        <BotaoInput label="Senha" onPress={() => {}} />
      </View>
      <View style={styles.buttonEntrar}>
        <BotaoVerde label="Entrar" onPress={() => router.push('../(tabs)/guides/allguides')} />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F8F8F8',
  },
  logo: {
    position: 'absolute',
    width: width * 0.5, // 50% da largura da tela
    height: height * 0.2, // 20% da altura da tela
    backgroundColor: '#D9D9D9',
    top: height * 0.2, 
    left: width * 0.25,
  },
  buttonGoogle:{
    position: 'absolute',
    top: height * 0.44, 
    left: width * 0.05, 
  },
  buttonEmail: {
    position: 'absolute',
    top: height * 0.55, 
    left: width * 0.05,
  },
  buttonPassword: {
    position: 'absolute',
    top: height * 0.64, 
    left: width * 0.05,
  },
  buttonEntrar: {
    position: 'absolute',
    top: height * 0.74, 
    left: width * 0.05,
  }
});
