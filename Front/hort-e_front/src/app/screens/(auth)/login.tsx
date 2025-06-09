import { useRouter } from 'expo-router';
import * as SecureStore from 'expo-secure-store';
import React, { useState } from 'react';
import { Alert, Dimensions, StyleSheet, View } from 'react-native';
import BotaoGoogle from '../../components/BotaoGoogle';
import BotaoInput from '../../components/BotaoInput';
import BotaoVerde from '../../components/BotaoVerde';

const { height, width } = Dimensions.get('window');

export default function Login() {
  const router = useRouter();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async () => {
    try {
      const response = await fetch('http://10.0.2.2:8080/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Erro ao fazer login');
      }

      const data = await response.json();
      const token = data.token;
      const id = data.id;

      // Armazenar o token no AsyncStorage
      await SecureStore.setItemAsync('authToken', token);
      await SecureStore.setItemAsync('userId', id.toString());

      // Navegar para próxima tela
      router.push('../(tabs)/guides/allguides');
    } catch (error: any) {
      Alert.alert('Erro de login', error.message);
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.logo} />
      <View style={styles.buttonGoogle}>
        <BotaoGoogle onPress={() => {}} />
      </View>
      <View style={styles.buttonEmail}>
        <BotaoInput
          label="Nome de Usuário"
          value={username}
          onChangeText={setUsername}
        />
      </View>
      <View style={styles.buttonPassword}>
        <BotaoInput
          label="Senha"
          value={password}
          onChangeText={setPassword}
          secureTextEntry
        />
      </View>
      <View style={styles.buttonEntrar}>
        <BotaoVerde label="Entrar" onPress={handleLogin} />
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
    width: width * 0.5,
    height: height * 0.2,
    backgroundColor: '#D9D9D9',
    top: height * 0.2,
    left: width * 0.25,
  },
  buttonGoogle: {
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
  },
});
