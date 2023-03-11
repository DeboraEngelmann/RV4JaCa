:- module('spec', [trace_expression/2, match/2]).
:- use_module(monitor('deep_subdict')).
match(_event, question(Ag1, Ag2)) :- deep_subdict(_{'receiver':Ag2,'sender':Ag1,'performative':"question"}, _event).
match(_event, answer(Ag1, Ag2)) :- deep_subdict(_{'receiver':Ag2,'sender':Ag1,'performative':"assert"}, _event).
match(_event, msg_between(Ag1, Ag2)) :- deep_subdict(_{'receiver':Ag2,'sender':Ag1}, _event).
match(_event, msg_between(Ag1, Ag2)) :- deep_subdict(_{'receiver':Ag1,'sender':Ag2}, _event).
match(_event, not_msg_between(Ag1, Ag2)) :- not(match(_event, msg_between(Ag1, Ag2))).
match(_event, any_answer) :- deep_subdict(_{'performative':"assert"}, _event).
match(_event, monitor_msg) :- deep_subdict(_{'sender':"monitor"}, _event).
match(_event, monitor_msg) :- deep_subdict(_{'receiver':"monitor"}, _event).
match(_event, msg_of_interest) :- match(_event, question(_, _)).
match(_event, msg_of_interest) :- match(_event, answer(_, _)).
match(_, any).
trace_expression('Main', Main) :- Main=((msg_of_interest>>(Question|star((monitor_msg:eps))));1), Question=var(ag1, var(ag2, (((question(var(ag1), var(ag2)):eps)*((msg_between(var(ag1), var(ag2))>>app(Answer, [var('ag1'), var('ag2')]));1))/\((not_msg_between(var(ag1), var(ag2))>>optional(Question));1)))), Answer=gen(['ag1', 'ag2'], ((answer(var(ag2), var(ag1)):eps)*star((app(MsgExchange, [var('ag1'), var('ag2')])\/app(MsgExchange, [var('ag2'), var('ag1')]))))), MsgExchange=gen(['ag1', 'ag2'], ((question(var(ag1), var(ag2)):eps)*(answer(var(ag2), var(ag1)):eps))).
