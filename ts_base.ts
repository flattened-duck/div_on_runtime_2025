import {
    DivContainer,
    DivText,
    DivState,
    DivInput,
    reference,
    divCard,
    templateHelper,
    rewriteRefs,
    expression
} from '@divkitframework/jsonbuilder';

const templates = {
    event_checkmark_default: new DivContainer({
        orientation: 'horizontal',
        content_alignment_vertical: 'center',
        margins: {
            top: 5,
            bottom: 5
        },
        
        actions: [
            {
                url: reference('action_url'),
                log_id: 'set_checkbox',
                is_enabled: expression('@{!is_locked}')
            }
        ],
        items: [
            new DivContainer({
                orientation: 'vertical',
                width: {
                    type: 'fixed',
                    value: 20
                },
                height: {
                    type: 'fixed',
                    value: 20
                },
                border: {
                    stroke: {
                        color: '#000',
                        width: 1,
                        style: {
                            type: 'solid'
                        }
                    }
                },
                margins: {
                    end: 7
                },
                content_alignment_vertical: 'center',
                items: [
                    new DivText({
                        visibility: reference('is_checked'),
                        text: 'X',
                        font_size: 14,
                        text_color: '#000000',
                        text_alignment_horizontal: 'center'
                    })
                ]
            }),
            new DivText({
                text: reference('title'),
                font_size: 14,
                text_color: '#000000'
            })
        ]
    })
};

const thelper = templateHelper(templates);

export function getJson() {
    return divCard(rewriteRefs(templates), {
        log_id: 'div2_sample_card',
        variables: [
            {
                name: 'is_locked',
                type: 'boolean',
                value: false
            },
            {
                name: 'is_locked_state',
                type: 'string',
                value: 'unlocked'
            },
            {
                name: 'coderun_check',
                type: 'boolean',
                value: false
            },
            {
                name: 'divkit_check',
                type: 'boolean',
                value: false
            },
            {
                name: 'workshop_check',
                type: 'boolean',
                value: false
            },
            {
                name: 'quiz_check',
                type: 'boolean',
                value: false
            },
            {
                name: 'hookah_check',
                type: 'boolean',
                value: false
            },
            {
                name: 'custom_checkmark_input_text',
                type: 'string',
                value: ''
            },
            {
                name: 'custom_checkmark_check',
                type: 'boolean',
                value: false
            }
        ],
        variable_triggers: [
            {
                mode: 'on_variable',
                condition: expression('@{is_locked || !is_locked}'),
                actions: [
                    {
                        url: expression('div-action://set_variable?name=is_locked_state&value=@{is_locked ? \'locked\' : \'unlocked\'}'),
                        log_id: 'change_lock_state'
                    }
                ]
            }
        ],
        states: [
            {
                state_id: 0,
                div: new DivContainer({
                    orientation: 'vertical',
                    items: [
                        thelper.event_checkmark_default({
                            title: 'Покодил на CodeRun',
                            action_url: expression('div-action://set_variable?name=coderun_check&value=@{!coderun_check}'),
                            is_checked: expression('@{coderun_check ? \'visible\' : \'gone\'}')
                        }),
                        thelper.event_checkmark_default({
                            title: 'Написал вёрстку на скорость',
                            action_url: expression('div-action://set_variable?name=divkit_check&value=@{!divkit_check}'),
                            is_checked: expression('@{divkit_check ? \'visible\' : \'gone\'}')
                        }),
                        thelper.event_checkmark_default({
                            action_url: expression('div-action://set_variable?name=workshop_check&value=@{!workshop_check}'),
                            is_checked: expression('@{workshop_check ? \'visible\' : \'gone\'}'),
                            title: 'Сходил на воркшоп'
                        }),
                        thelper.event_checkmark_default({
                            title: 'Угадал все ответы на квизе',
                            action_url: expression('div-action://set_variable?name=quiz_check&value=@{!quiz_check}'),
                            is_checked: expression('@{quiz_check ? \'visible\' : \'gone\'}')
                        }),
                        thelper.event_checkmark_default({
                            title: 'Посмотрел на кальян',
                            action_url: expression('div-action://set_variable?name=hookah_check&value=@{!hookah_check}'),
                            is_checked: expression('@{hookah_check ? \'visible\' : \'gone\'}')
                        }),
                        // Кастомный чекбокс с инпутом
                        new DivContainer({
                            orientation: 'horizontal',
                            content_alignment_vertical: 'center',
                            margins: {
                                top: 5,
                                bottom: 5
                            },
                            variables: [
                                {
                                    name: 'is_checked',
                                    type: 'boolean',
                                    value: false
                                }
                            ],
                            items: [
                                new DivContainer({
                                    orientation: 'vertical',
                                    width: {
                                        type: 'fixed',
                                        value: 20
                                    },
                                    height: {
                                        type: 'fixed',
                                        value: 20
                                    },
                                    border: {
                                        stroke: {
                                            color: '#000',
                                            width: 1,
                                            style: {
                                                type: 'solid'
                                            }
                                        }
                                    },
                                    margins: {
                                        end: 7
                                    },
                                    content_alignment_vertical: 'center',
                                    actions: [
                                        {
                                            url: expression('div-action://set_variable?name=custom_checkmark_check&value=@{!custom_checkmark_check}'),
                                            log_id: 'set_checkbox',
                                            is_enabled: expression('@{!is_locked}')
                                        }
                                    ],
                                    items: [
                                        new DivText({
                                            text: 'X',
                                            font_size: 14,
                                            text_color: '#000000',
                                            text_alignment_horizontal: 'center',
                                            visibility: expression('@{custom_checkmark_check ? \'visible\' : \'gone\'}')
                                        })
                                    ]
                                }),
                                new DivState({
                                    id: 'cutom_checkmark',
                                    state_id_variable: 'is_locked_state',
                                    states: [
                                        {
                                            state_id: 'locked',
                                            div: new DivText({
                                                text: expression('@{custom_checkmark_input_text}'),
                                                font_size: 14,
                                                text_color: '#000000'
                                            })
                                        },
                                        {
                                            state_id: 'unlocked',
                                            div: new DivInput({
                                                text_variable: 'custom_checkmark_input_text',
                                                keyboard_type: 'single_line_text',
                                                width: {
                                                    type: 'fixed',
                                                    value: 200
                                                },
                                                border: {
                                                    stroke: {
                                                        color: '#000',
                                                        width: 1,
                                                        style: {
                                                            type: 'solid'
                                                        }
                                                    }
                                                }
                                            })
                                        }
                                    ]
                                }),
                            ]
                        })
                    ]
                })
            }
        ]
    });
}
