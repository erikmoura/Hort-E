import React from 'react';
import { Dimensions, StyleSheet, TextInput, View } from 'react-native';

type PropsBotaoInput = {
  label: string;
  value: string;
  onChangeText: (text: string) => void;
  secureTextEntry?: boolean;
};

const { height, width } = Dimensions.get('window');

export default function PesquisaPlantas({
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
    borderColor: '#000000',
    borderWidth: 1,
    borderRadius: 10,
    width: width * 0.9,
    height: height * 0.065,
    paddingLeft: 15,
    color: '#000000',
  },
});
