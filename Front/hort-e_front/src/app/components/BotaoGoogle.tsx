import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Dimensions } from 'react-native';

type propsBotao = {
  onPress: () => void;
}

const { height, width } = Dimensions.get('window');

export default function BotaoGoogle({onPress}: propsBotao) {
  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.button} onPress={onPress}>
        <View style={styles.viewBotao}>
            <Text style={styles.buttonText}>(logo)</Text>
            <Text style={styles.buttonText}>Continuar com o Google</Text>
        </View>
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
  viewBotao: {
    display: 'flex',
    flexDirection: 'row',
    paddingLeft: width * 0.04,
    gap: width * 0.035,
  },
  button : {
    backgroundColor: '#FFFFFF', 
    borderRadius: 10,
    borderColor: '#000000',
    borderWidth: 1.5,
    width: width * 0.9,
    height: height * 0.06,
    justifyContent: 'center',
    },
  buttonText: {
    color: '#000000',
    fontSize: 14,
    lineHeight: 20,
    fontWeight: 'regular', 
  },
});
