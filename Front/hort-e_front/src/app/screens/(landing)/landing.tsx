import React from 'react';
import { View, Text, StyleSheet, Dimensions } from 'react-native';
import { useRouter } from 'expo-router';
import BotaoVerde from '../../components/BotaoVerde';
import BannerInicial from '../../components/BannerInicial';

const { height, width } = Dimensions.get('window');

export default function LandingPage() {

  const router = useRouter();

  return (
    <View style={styles.container}>

      <BannerInicial />

      <View style={styles.button}>
        <BotaoVerde label="Entrar" onPress={() => router.push('../screens/(auth)/login')}/>
      </View>

      <Text style={styles.footerText}>
        Não possui conta? <Text style={styles.linkText}>Cadastre-se.</Text>
      </Text>
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
    bottom: height * 0.055,
    color: '#333',
  },
  linkText: {
    color: '#2E7D32',
    fontWeight: '600',
  },
});

