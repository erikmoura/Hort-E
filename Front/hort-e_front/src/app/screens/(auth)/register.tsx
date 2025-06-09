import * as Location from 'expo-location';
import { useRouter } from 'expo-router';
import React, { useState } from 'react';
import { Alert, Dimensions, StyleSheet, View } from 'react-native';
import BotaoInput from '../../components/BotaoInput';
import BotaoVerde from '../../components/BotaoVerde';

const { height, width } = Dimensions.get('window');

export default function Register() {
  const router = useRouter();

  const [usuarioEmail, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [senha, setPassword] = useState('');
  const [localizacao, setLocalizacao] = useState('');
  // const [usuarioImagemUrl, setImage] = useState('');

  const obterLocalizacao = async () => {
    try {
      // pede permissao p acessar loc
      const { status } = await Location.requestForegroundPermissionsAsync();
      if (status !== 'granted') {
        Alert.alert('Permissão negada', 'Não foi possível obter a localização.');
        return;
      }

      const location = await Location.getCurrentPositionAsync({});
      const { latitude, longitude } = location.coords;

      // chama opencage
      const response = await fetch(
        `https://api.opencagedata.com/geocode/v1/json?q=${latitude}+${longitude}&key=f77e5ce7c94349d69ec217d637963360`
      );
      const data = await response.json();

      if (data.results.length > 0) {
        const components = data.results[0].components;
        const cidade = components.city || components.town || components.village || '';
        const estado = components.state || '';
        const local = `${cidade}, ${estado}`;
        setLocalizacao(local);
      } else {
        Alert.alert('Erro', 'Endereço não encontrado.');
      }
    } catch (error) {
      console.error(error);
      Alert.alert('Erro', 'Erro ao obter a localização.');
    }
  };

  const handleCadastro = async () => {
    const usuario = {
      username,
      senha,
      usuarioEmail,
      localizacao,
      usuarioImagemUrl: "https://images.pexels.com/photos/819530/pexels-photo-819530.jpeg?_gl=1*q03l92*_ga*MjU5MTg3NDQzLjE3NDk0Njk5NDE.*_ga_8JE65Q40S6*czE3NDk0Njk5NDAkbzEkZzEkdDE3NDk0Njk5NDkkajUxJGwwJGgw",
      hortaTipo: 2,
    };

    try {
      const response = await fetch('http://10.0.2.2:8080/api/auth/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(usuario),
      });
      if (response.ok) {
        Alert.alert('Sucesso', 'Cadastro realizado com sucesso!');
        router.replace('/screens/(auth)/login'); 
      } else {
        const error = await response.json();
        Alert.alert('Erro', error.message || 'Falha no cadastro');
      }
    } catch (err) {
      console.error(err);
      Alert.alert('Erro', 'Não foi possível conectar ao servidor.');
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.logo} />
      <View style={styles.buttonEmail}>
        <BotaoInput
          label="Email"
          value={usuarioEmail}
          onChangeText={setEmail}
        />
      </View>
      <View style={styles.buttonUsername}>
        <BotaoInput
          label="Nome de Usuário"
          value={username}
          onChangeText={setUsername}
        />
      </View>
      <View style={styles.buttonPassword}>
        <BotaoInput
          label="Senha"
          value={senha}
          onChangeText={setPassword}
          secureTextEntry
        />
      </View>
      <View style={styles.buttonLocation}>
        <BotaoInput
          label="Localização"
          value={localizacao}
          onChangeText={() => {}}
          onPress={obterLocalizacao} 
        />
      </View>
      <View style={styles.buttonEntrar}>
        <BotaoVerde label="Cadastrar" onPress={handleCadastro} />
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
  buttonEmail: {
    position: 'absolute',
    top: height * 0.44,
    left: width * 0.05,
  },
  buttonUsername: {
    position: 'absolute',
    top: height * 0.52,
    left: width * 0.05,
  },
  buttonPassword: {
    position: 'absolute',
    top: height * 0.60,
    left: width * 0.05,
  },
  buttonLocation: {
    position: 'absolute',
    top: height * 0.68,
    left: width * 0.05,
  },
  buttonEntrar: {
    position: 'absolute',
    top: height * 0.78,
    left: width * 0.05,
  },
});
