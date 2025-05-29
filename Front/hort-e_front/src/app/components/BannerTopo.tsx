import React from 'react';
import { View, Text, StyleSheet, Dimensions} from 'react-native';


const { height, width } = Dimensions.get('window');

type propsBanner = {
    title: string;
}

export default function BannerTopo({title}: propsBanner) {

    return (
        <View style={styles.container}>
            <Text style={styles.title}>{title}</Text>
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
        height: height * 0.12,
        paddingTop: height * 0.01,
    },
    title: {
        fontSize: 40,
        fontWeight: 'bold',
        color: '#FFFFFF',
    },
});