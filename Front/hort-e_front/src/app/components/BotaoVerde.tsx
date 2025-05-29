import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Dimensions } from 'react-native';

type propsBotao = {
  label: string;
  onPress: () => void;
}

const { height, width } = Dimensions.get('window');

export default function BotaoVerde({label, onPress}: propsBotao) {
  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.button} onPress={onPress}>
        <Text style={styles.buttonText}>{label}</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    justifyContent: 'center',
    alignItems: 'center',
    width:'100%',
  },
  button : {
    backgroundColor: '#257D3C', 
    borderRadius: 10,
    width: width * 0.9,
    height: height * 0.07,
    alignItems: 'center',
    justifyContent: 'center',
    elevation: 5,
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
    },
  buttonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: 'bold', 
  },
});
