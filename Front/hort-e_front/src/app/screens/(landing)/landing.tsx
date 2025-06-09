import { useRouter } from 'expo-router';
import React from 'react';
import { Dimensions, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import BannerInicial from '../../components/BannerInicial';
import BotaoVerde from '../../components/BotaoVerde';

const { height, width } = Dimensions.get('window');

export default function LandingPage() {

  const router = useRouter();

  return (
    <View style={styles.container}>

      <BannerInicial />

      <View style={styles.button}>
        <BotaoVerde label="Entrar" onPress={() => router.push('/screens/(auth)/login')}/>
      </View>

      <View style={styles.footerText}>
        <Text>
          Não possui conta?
        </Text>
        <TouchableOpacity onPress={() => router.push('/screens/(auth)/register')}>
          <Text style={styles.linkText}>Cadastre-se.</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    display: 'flex',
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  button: {
    position: 'absolute',
    bottom: height * 0.098,
  },
  buttonText: {
    color: '#fff',
    fontWeight: 'bold',
    fontSize: 16,
  },
  footerText: {
    position: 'absolute',
    flexDirection: 'row',
    gap: 5,
    bottom: height * 0.055,
    color: '#333',
  },
  linkContainer:{
    position: 'absolute',
    bottom: height * 0.01,

  },
  linkText: {
    color: '#2E7D32',
    fontWeight: '600',
  },
});

