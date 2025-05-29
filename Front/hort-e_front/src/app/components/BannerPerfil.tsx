import React from 'react';
import { View, StyleSheet, Dimensions} from 'react-native';


const { height, width } = Dimensions.get('window');


export default function BannerPerfil() {

    return (
        <View style={styles.container}>
        </View>

    );

};

const styles = StyleSheet.create({
    container: {
        display: 'flex',
        justifyContent: 'center',
        paddingLeft: width * 0.05,
        backgroundColor: '#257D3C',
        width: '100%',
        height: height * 0.15,
        paddingTop: height * 0.01,
    }
});