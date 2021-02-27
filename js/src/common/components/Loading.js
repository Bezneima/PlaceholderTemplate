import React from 'react';

type Props = {
    error?: Error | null | undefined;
};

const Loading = ({error}: Props) => {
    if (error) {
        console.error(error);

        return <div>
            Что-то пошло не так...
            {error.message}
        </div>;
    }

    return <div>Грузим...</div>;
};

export default Loading;