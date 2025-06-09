import React from 'react';
import { View, StyleSheet, TextInput, Dimensions } from 'react-native';

type PropsBotaoInput = {
  label: string;
  value: string;
  onChangeText: (text: string) => void;
  secureTextEntry?: boolean;
};

const { height, width } = Dimensions.get('window');

export default function BotaoInput({
  label,
  value,
  onChangeText,
  secureTextEntry = false,
}: PropsBotaoInput) {
  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder={label}
        placeholderTextColor="#828282"
        value={value}
        onChangeText={onChangeText}
        secureTextEntry={secureTextEntry}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    justifyContent: 'center',
    alignItems: 'center',
    width: '100%',
  },
  input: {
    backgroundColor: '#ffffff',
    borderColor: '#D9D9D9',
    borderWidth: 1,
    borderRadius: 10,
    width: width * 0.9,
    height: height * 0.07,
    paddingLeft: 15,
    color: '#000000',
  },
});
