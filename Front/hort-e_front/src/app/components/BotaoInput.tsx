import React from 'react';
import { View, StyleSheet, TextInput, Dimensions } from 'react-native';

type propsBotao = {
  label: string;
  onPress: () => void;
}

const { height, width } = Dimensions.get('window');

export default function BotaoInput({label, onPress}: propsBotao) {
  return (
    <View style={styles.container}>
      <TextInput style={styles.button} placeholder = {label} onPress={onPress} placeholderTextColor="#828282">
      </TextInput>
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
    backgroundColor: '#ffffff',
    borderColor: '#D9D9D9', 
    borderWidth: 1,
    borderStyle: 'solid',
    borderRadius: 10,
    width: width * 0.9,
    height: height * 0.07,
    justifyContent: 'center',
    boxShadow: '1px 1px 6px rgba(0, 0, 0, 0.1)',
    paddingLeft: 15,
    },
  buttonText: {
    color: '#828282',
    fontSize: 16,
    fontWeight: 'regular',
    marginLeft: 15, 
  },
});

